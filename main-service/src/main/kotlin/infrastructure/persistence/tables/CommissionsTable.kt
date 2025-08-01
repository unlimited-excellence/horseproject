package infrastructure.persistence.tables

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import eth.likespro.commons.models.value.Timestamp
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object CommissionsTable : Table("commissions") {
    val id = varchar("id", 36).uniqueIndex()
    val competitionId = varchar("competition_id", 36)
    val name = varchar("name", 64)
    val startedAt = timestamp("started_at").nullable()
    val endedAt = timestamp("ended_at").nullable()

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(competitionId, name)
    }

    fun fromRow(row: ResultRow): Commission = Commission(
        id = Commission.Id(row[id]),
        competitionId = Competition.Id(row[competitionId]), // Это должно быть Competition.Id, но структура Competition.kt подразумевает, что поле имеет ту же структуру идентификатора, что и Commission.Id
        name = Commission.Name(row[name]),
        startedAt = row[startedAt]?.let { Timestamp(it) },
        endedAt = row[endedAt]?.let { Timestamp(it) }
    )

    fun copy(commission: Commission, to: UpdateBuilder<Int>) {
        to[id] = commission.id.value
        to[competitionId] = commission.competitionId.value
        to[name] = commission.name.value
        to[startedAt] = commission.startedAt?.toInstant()
        to[endedAt] = commission.endedAt?.toInstant()
    }
}