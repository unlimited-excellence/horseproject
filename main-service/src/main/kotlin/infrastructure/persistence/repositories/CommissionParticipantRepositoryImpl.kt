package infrastructure.persistence.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import domain.ports.repositories.CommissionParticipantRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Pagination
import infrastructure.persistence.tables.CommissionParticipantsTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
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
}