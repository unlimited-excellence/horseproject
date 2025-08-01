package infrastructure.persistence.tables

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.WineSample
import eth.likespro.commons.models.value.Timestamp
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object CompetitionsTable : Table("competitions") {
    val id = varchar("id", 36).uniqueIndex()
    val organizer = long("organizer")
    val name = text("name").uniqueIndex()
    val status = enumerationByName("color", 30, Competition.Status::class)
    val currentWine = varchar("current_wine", 36).nullable()
    val plannedStartAt = timestamp("planned_start_at")

    override val primaryKey = PrimaryKey(id)

    fun fromRow(row: ResultRow) = Competition(
        id = Competition.Id(row[id]),
        organizer = AUID(row[organizer]),
        name = Competition.Name(row[name]),
        status = row[status],
        plannedStartAt = Timestamp(row[plannedStartAt]),
    )

    fun copy(competition: Competition, to: UpdateBuilder<Int>) {
        to[id] = competition.id.value
        to[organizer] = competition.organizer.value
        to[name] = competition.name.value
        to[status] = competition.status
        to[plannedStartAt] = competition.plannedStartAt.toInstant()
    }
}