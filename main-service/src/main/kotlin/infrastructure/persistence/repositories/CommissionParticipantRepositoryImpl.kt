package infrastructure.persistence.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import domain.ports.repositories.CommissionParticipantRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Pagination
import infrastructure.persistence.tables.CommissionParticipantsTable
import infrastructure.persistence.tables.CompetitionsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CommissionParticipantRepositoryImpl : CommissionParticipantRepository {
    init {
        transaction {
            SchemaUtils.create(CommissionParticipantsTable)
        }
    }

    override suspend fun isExistingByCommissionIdAndAUID(
        atom: Atom,
        commissionId: Commission.Id,
        auid: AUID
    ): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !CommissionParticipantsTable.selectAll()
            .andWhere { CommissionParticipantsTable.commissionId eq commissionId.value }
            .andWhere { CommissionParticipantsTable.auid eq auid.value }
            .empty()
    }

    override suspend fun findByCommissionIdAndAUID(
        atom: Atom,
        commissionId: Commission.Id,
        auid: AUID
    ): CommissionParticipant? = AtomarixExposedAdapter.runWithAdapter(atom) {
        CommissionParticipantsTable.selectAll()
            .andWhere { CommissionParticipantsTable.commissionId eq commissionId.value }
            .andWhere { CommissionParticipantsTable.auid eq auid.value }
            .singleOrNull()
            ?.let { CommissionParticipantsTable.fromRow(it) }
    }

    override suspend fun filter(
        atom: Atom,
        commissionId: Commission.Id?,
        auid: AUID?,
        role: CommissionParticipant.Role?,
        pagination: Pagination
    ): List<CommissionParticipant> = AtomarixExposedAdapter.runWithAdapter(atom) {
        filterQuery(commissionId, auid, role)
            .offset(pagination.offset).limit(pagination.itemsPerPage)
            .map { CommissionParticipantsTable.fromRow(it) }
    }

    override suspend fun count(atom: Atom, pagination: Pagination?): Long {
        TODO("Not yet implemented")
    }

    override suspend fun create(atom: Atom, entity: CommissionParticipant): CommissionParticipant = AtomarixExposedAdapter.runWithAdapter(atom) {
        CommissionParticipantsTable.insert {
            CommissionParticipantsTable.copy(entity, it)
        }.let { entity }
    }

    override suspend fun delete(atom: Atom, id: CommissionParticipant.Id) {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(atom: Atom, pagination: Pagination): List<CommissionParticipant> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(atom: Atom, id: CommissionParticipant.Id): CommissionParticipant? {
        TODO("Not yet implemented")
    }

    override suspend fun isExisting(atom: Atom, id: CommissionParticipant.Id): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(atom: Atom, entity: CommissionParticipant): CommissionParticipant? {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(atom: Atom, entity: CommissionParticipant): CommissionParticipant {
        TODO("Not yet implemented")
    }

    fun filterQuery(commissionId: Commission.Id?, auid: AUID?, role: CommissionParticipant.Role?): Query = CommissionParticipantsTable.selectAll()
        .let { if(commissionId != null) it.andWhere { CommissionParticipantsTable.commissionId eq commissionId.value } else it }
        .let { if(auid != null) it.andWhere { CommissionParticipantsTable.auid eq auid.value } else it }
        .let { if(role != null) it.andWhere { CommissionParticipantsTable.role eq role } else it }
}