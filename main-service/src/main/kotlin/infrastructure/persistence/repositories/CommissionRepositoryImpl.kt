package infrastructure.persistence.repositories

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.WineSample
import domain.ports.repositories.CommissionRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Pagination
import eth.likespro.commons.models.value.Timestamp
import infrastructure.persistence.tables.CommissionsTable
import infrastructure.persistence.tables.CompetitionsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CommissionRepositoryImpl : CommissionRepository {
    init {
        transaction {
            SchemaUtils.create(CommissionsTable)
        }
    }

    override suspend fun findByCompetitionId(atom: Atom, competitionId: Competition.Id): List<Commission> = AtomarixExposedAdapter.runWithAdapter(atom) {
        CommissionsTable.selectAll()
            .andWhere { CommissionsTable.competitionId eq competitionId.value }
            .map { CommissionsTable.fromRow(it) }
    }

    override suspend fun findByCompetitionIdAndName(atom: Atom, competitionId: Competition.Id, name: Commission.Name): Commission? = AtomarixExposedAdapter.runWithAdapter(atom) {
        CommissionsTable.selectAll()
            .andWhere { 
                (CommissionsTable.competitionId eq competitionId.value) and
                (CommissionsTable.name eq name.value)
            }
            .singleOrNull()
            ?.let { CommissionsTable.fromRow(it) }
    }

    override suspend fun isExistingByCompetitionIdAndName(atom: Atom, competitionId: Competition.Id, name: Commission.Name): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !CommissionsTable.selectAll()
            .andWhere { 
                (CommissionsTable.competitionId eq competitionId.value) and
                (CommissionsTable.name eq name.value)
            }
            .empty()
    }

    override suspend fun updateStartedAtAndCurrentWineSampleId(
        atom: Atom,
        id: Commission.Id,
        startedAt: Timestamp?,
        currentWineSampleId: WineSample.Id
    ) = AtomarixExposedAdapter.runWithAdapter(atom) {
        CommissionsTable.update({ CommissionsTable.id eq id.value }) {
            it[CommissionsTable.startedAt] = startedAt?.toInstant()
            it[CommissionsTable.currentWineSampleId] = currentWineSampleId.value
        }.let {  }
    }

    override suspend fun updateCurrentWineSampleId(atom: Atom, id: Commission.Id, currentWineSampleId: Wine.Id) = AtomarixExposedAdapter.runWithAdapter(atom) {
        CommissionsTable.update({ CommissionsTable.id eq id.value })
        { it[CommissionsTable.currentWineSampleId] = currentWineSampleId.value }
            .let {  }
    }

    override suspend fun count(atom: Atom, pagination: Pagination?): Long {
        TODO("Not yet implemented")
    }

    override suspend fun create(atom: Atom, entity: Commission): Commission = AtomarixExposedAdapter.runWithAdapter(atom) {
        CommissionsTable.insert {
            CommissionsTable.copy(entity, it)
        }.let { entity }
    }

    override suspend fun delete(atom: Atom, id: Commission.Id) {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(atom: Atom, pagination: Pagination): List<Commission> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(atom: Atom, id: Commission.Id): Commission? = AtomarixExposedAdapter.runWithAdapter(atom) {
        CommissionsTable.selectAll()
            .andWhere { CommissionsTable.id eq id.value }
            .singleOrNull()
            ?.let { CommissionsTable.fromRow(it) }
    }

    override suspend fun isExisting(atom: Atom, id: Commission.Id): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !CommissionsTable.selectAll()
            .andWhere { CommissionsTable.id eq id.value }
            .empty()
    }

    override suspend fun update(atom: Atom, entity: Commission): Commission? {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(atom: Atom, entity: Commission): Commission {
        TODO("Not yet implemented")
    }
}