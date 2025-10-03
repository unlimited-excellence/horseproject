// file: main-service/src/main/kotlin/infrastructure/persistence/DatabaseFactory.kt
package infrastructure.persistence

import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import infrastructure.persistence.tables.TransactionsTable

/**
 * Database bootstrap with dual-mode support:
 *
 * - DB mode:
 *     If POSTGRESQL_URI (and credentials) are provided and DISABLE_DB != true,
 *     we connect to PostgreSQL and (optionally) create missing tables/columns.
 *
 * - No-DB mode:
 *     If DISABLE_DB=true OR POSTGRESQL_URI is blank, we skip DB init.
 *     The service still starts and serves /health and lightweight REST endpoints.
 *
 * Env vars:
 *   - DISABLE_DB=true|false (optional; default false)
 *   - POSTGRESQL_URI=jdbc:postgresql://HOST:PORT/DB
 *   - POSTGRESQL_USERNAME=...
 *   - POSTGRESQL_PASSWORD=...
 */
object DatabaseFactory {

    private val logger = KotlinLogging.logger {}

    @Volatile
    private var _enabled: Boolean = false
    val enabled: Boolean get() = _enabled

    fun init() {
        val disableDb = System.getenv("DISABLE_DB")?.equals("true", ignoreCase = true) == true
        val url = System.getenv("POSTGRESQL_URI")?.trim().orEmpty()
        val user = System.getenv("POSTGRESQL_USERNAME")?.trim().orEmpty()
        val pass = System.getenv("POSTGRESQL_PASSWORD")?.trim().orEmpty()

        // No-DB mode if explicitly disabled or URI is missing/blank
        if (disableDb || url.isBlank()) {
            _enabled = false
            logger.warn {
                "DB initialization is skipped (DISABLE_DB=$disableDb, POSTGRESQL_URI blank=${url.isBlank()}). " +
                        "Running in NO-DB mode: only /health and basic REST endpoints will be available."
            }
            return
        }

        // Try to connect (DB mode)
        try {
            logger.info { "Initializing database connection (PostgreSQL)..." }
            Database.connect(
                url = url,
                driver = "org.postgresql.Driver",
                user = user,
                password = pass
            )

            // Optional: ensure schema is in place (safe to keep; no-op if already created)
            transaction {
                SchemaUtils.createMissingTablesAndColumns(TransactionsTable)
            }

            _enabled = true
            logger.info { "Database initialized successfully (DB mode enabled)." }
        } catch (t: Throwable) {
            // Fall back to NO-DB mode but keep the service alive
            _enabled = false
            logger.error(t) {
                "Database initialization failed. Falling back to NO-DB mode. " +
                        "The service will run without DB access."
            }
        }
    }

    /**
     * Liveness probe for /health: returns true even in NO-DB mode so Cloud Run can start routing traffic.
     */
    fun livenessOk(): Boolean = true

    /**
     * Optional readiness check for DB consumers.
     * Returns true only if DB mode is enabled and a simple transaction succeeds.
     */
    fun dbReady(): Boolean {
        if (!enabled) return false
        return runCatching {
            transaction { /* lightweight transaction */ }
            true
        }.getOrElse { false }
    }
}
