package infrastructure.persistence.repositories

import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.Competition
import domain.ports.repositories.CompetitionRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Pagination
import infrastructure.persistence.tables.CompetitionsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CompetitionRepositoryImpl : CompetitionRepository {
    init {
        transaction {
            SchemaUtils.create(CompetitionsTable)
        }
    }

    override suspend fun isExistingByName(atom: Atom, name: Competition.Name): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !CompetitionsTable.selectAll()
            .andWhere { CompetitionsTable.name eq name.value }
            .empty()
    }

    override suspend fun findByName(atom: Atom, name: Competition.Name): Competition? = AtomarixExposedAdapter.runWithAdapter(atom) {
        CompetitionsTable.selectAll()
            .andWhere { CompetitionsTable.name eq name.value }
            .singleOrNull()
            ?.let { CompetitionsTable.fromRow(it) }
    }

    override suspend fun updateStatus(
        atom: Atom,
        id: Competition.Id,
        status: Competition.Status
    ) = AtomarixExposedAdapter.runWithAdapter(atom) {
        CompetitionsTable.update({ CompetitionsTable.id eq id.value })
            { it[CompetitionsTable.status] = status }
            .let {  }
    }

    override suspend fun updateCurrentWineId(atom: Atom, id: Competition.Id, currentWine: Wine.Id) = AtomarixExposedAdapter.runWithAdapter(atom) {
        CompetitionsTable.update({ CompetitionsTable.id eq id.value })
            { it[CompetitionsTable.currentWine] = currentWine.value }
            .let {  }
    }

    override suspend fun count(atom: Atom, pagination: Pagination?): Long {
        TODO("Not yet implemented")
    }

    override suspend fun create(atom: Atom, entity: Competition): Competition = AtomarixExposedAdapter.runWithAdapter(atom) {
        CompetitionsTable.insert {
            CompetitionsTable.copy(entity, it)
        }.let { entity }
    }

    override suspend fun delete(atom: Atom, id: Competition.Id) {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(atom: Atom, pagination: Pagination): List<Competition> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(atom: Atom, id: Competition.Id): Competition? = AtomarixExposedAdapter.runWithAdapter(atom){
        CompetitionsTable.selectAll()
            .andWhere { CompetitionsTable.id eq id.value }
            .singleOrNull()
            ?.let { CompetitionsTable.fromRow(it) }
    }

    override suspend fun isExisting(atom: Atom, id: Competition.Id): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !CompetitionsTable.selectAll()
            .andWhere { CompetitionsTable.id eq id.value }
            .empty()
    }

    override suspend fun update(atom: Atom, entity: Competition): Competition? {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(atom: Atom, entity: Competition): Competition {
        TODO("Not yet implemented")
    }
}