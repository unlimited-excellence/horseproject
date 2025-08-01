package infrastructure.persistence.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Wine
import domain.ports.repositories.WineRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Pagination
import eth.likespro.commons.models.value.Iteration
import infrastructure.persistence.tables.WinesTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class WineRepositoryImpl: WineRepository {
    init {
        transaction {
            SchemaUtils.create(WinesTable)
        }
    }

    override suspend fun isExistingByProducerAndNameAndIteration(
        atom: Atom,
        producer: AUID,
        name: Wine.Name,
        iteration: Iteration
    ): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !WinesTable.selectAll()
            .andWhere { WinesTable.producer eq producer.value }
            .andWhere { WinesTable.name eq name.value }
            .andWhere { WinesTable.iteration eq iteration.value }
            .empty()
    }

    override suspend fun findByProducerAndNameAndIteration(
        atom: Atom,
        producer: AUID,
        name: Wine.Name,
        iteration: Iteration
    ): Wine? = AtomarixExposedAdapter.runWithAdapter(atom) {
        WinesTable.selectAll()
            .andWhere { WinesTable.producer eq producer.value }
            .andWhere { WinesTable.name eq name.value }
            .andWhere { WinesTable.iteration eq iteration.value }
            .singleOrNull()
            .let { it?.let { WinesTable.fromRow(it)} }
    }

    override suspend fun count(atom: Atom, pagination: Pagination?): Long {
        TODO("Not yet implemented")
    }

    override suspend fun create(atom: Atom, entity: Wine): Wine = AtomarixExposedAdapter.runWithAdapter(atom) {
        WinesTable.insert {
            WinesTable.copy(entity, it)
        }.let { entity }
    }

    override suspend fun delete(atom: Atom, id: Wine.Id) {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(atom: Atom, pagination: Pagination): List<Wine> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(atom: Atom, id: Wine.Id): Wine? = AtomarixExposedAdapter.runWithAdapter(atom) {
        WinesTable.selectAll()
            .andWhere { WinesTable.id eq id.value }
            .singleOrNull()
            .let { it?.let { WinesTable.fromRow(it)} }
    }

    override suspend fun isExisting(atom: Atom, id: Wine.Id): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !WinesTable.selectAll()
            .andWhere { WinesTable.id eq id.value }
            .empty()
    }

    override suspend fun update(atom: Atom, entity: Wine): Wine? {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(atom: Atom, entity: Wine): Wine {
        TODO("Not yet implemented")
    }

}