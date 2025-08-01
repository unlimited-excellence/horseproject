package infrastructure.persistence.tables

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.CommissionParticipant
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object CommissionParticipantsTable : Table("commission_participants") {
    val id = varchar("id", 36).uniqueIndex()
    val commissionId = varchar("commission_id", 36)
    val auid = long("auid")
    val role = enumerationByName("role", 30, CommissionParticipant.Role::class)

    override val primaryKey = PrimaryKey(id)
    init {
        uniqueIndex(commissionId, auid)
    }

    fun fromRow(row: ResultRow) = CommissionParticipant(
        id = CommissionParticipant.Id(row[id]),
        commissionId = Commission.Id(row[commissionId]),
        auid = AUID(row[auid]),
        role = row[role],
    )

    fun copy(commissionParticipant: CommissionParticipant, to: UpdateBuilder<Int>) {
        to[id] = commissionParticipant.id.value
        to[commissionId] = commissionParticipant.commissionId.value
        to[auid] = commissionParticipant.auid.value
        to[role] = commissionParticipant.role
    }
}