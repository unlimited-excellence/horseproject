package presentation.rest

import eth.likespro.commons.models.WrappedException
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import org.unlimitedexcellence.horseproject.HorseProjectEndpoint

// Injected gateway (kept for future REST use, not used by /health)
val lpfcpGateway: HorseProjectEndpoint by inject(HorseProjectEndpoint::class.java)

// LPFCP exception details config stays as before
val exceptionDetailsConfiguration =
    WrappedException.DetailsConfiguration.INCLUDE_MESSAGE_AND_CAUSE

/**
 * Registers REST routes.
 * Adds:
 *  - GET /        -> simple info and hints
 *  - GET /health  -> health probe for Cloud Run/CI
 */
fun Application.configureREST() {
    install(ContentNegotiation) {
        gson { }
    }

    routing {
        // Human-friendly root endpoint: quick project info
        get("/") {
            call.respond(
                mapOf(
                    "service" to "horseproject main-service",
                    "status" to "ok",
                    "lpFcpMountedAt" to "/lpfcp",
                    "docs" to "/openapi (if exposed separately)",
                )
            )
        }

        // Health endpoint for Cloud Run, load balancers and CI checks
        get("/health") {
            // If you want to add deeper checks (DB ping, etc.), do it here.
            // For now: return 200 OK and a small JSON.
            call.respond(HttpStatusCode.OK, mapOf("ok" to true))
        }
    }
}
