package infrastructure.persistence.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import com.axus.winelore.model.entity.Competition
import domain.ports.repositories.AbstractCommissionRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Pagination
import infrastructure.persistence.tables.CommissionParticipantsTable
import infrastructure.persistence.tables.CommissionsTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll

class AbstractCommissionRepositoryImpl : AbstractCommissionRepository {
    override suspend fun findCommissionsByParticipant(
        atom: Atom,
        auid: AUID,
        pagination: Pagination
    ): List<Pair<Commission, CommissionParticipant>> = AtomarixExposedAdapter.runWithAdapter(atom) {
        CommissionParticipantsTable
            .join(CommissionsTable, JoinType.INNER, CommissionParticipantsTable.commissionId, CommissionsTable.id)
            .selectAll()
            .andWhere { CommissionParticipantsTable.auid eq auid.value }
            .offset(pagination.offset).limit(pagination.itemsPerPage)
            .map { CommissionsTable.fromRow(it) to CommissionParticipantsTable.fromRow(it) }
    }
}