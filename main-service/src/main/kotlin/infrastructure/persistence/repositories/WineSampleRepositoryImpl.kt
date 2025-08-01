package infrastructure.persistence.repositories

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.WineSample
import domain.ports.repositories.WineSampleRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Pagination
import infrastructure.persistence.tables.WineSamplesTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class WineSampleRepositoryImpl : WineSampleRepository {
    init {
        transaction {
            SchemaUtils.create(WineSamplesTable)
        }
    }

    override suspend fun isExistingByCommissionIdAndCode(
        atom: Atom,
        commissionId: Commission.Id,
        code: WineSample.Code
    ): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !WineSamplesTable.selectAll()
            .andWhere { WineSamplesTable.commissionId eq commissionId.value }
            .andWhere { WineSamplesTable.code eq code.value }
            .empty()
    }

    override suspend fun findByCommissionIdAndCode(
        atom: Atom,
        commissionId: Commission.Id,
        code: WineSample.Code
    ): WineSample? = AtomarixExposedAdapter.runWithAdapter(atom) {
        WineSamplesTable.selectAll()
            .andWhere { WineSamplesTable.commissionId eq commissionId.value }
            .andWhere { WineSamplesTable.code eq code.value }
            .singleOrNull()
            ?.let { WineSamplesTable.fromRow(it) }
    }

    override suspend fun isExistingByCommissionIdAndPreviousWineSampleId(
        atom: Atom,
        commissionId: Commission.Id,
        previousWineSampleId: WineSample.Id
    ): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !WineSamplesTable.selectAll()
            .andWhere { WineSamplesTable.commissionId eq commissionId.value }
            .andWhere { WineSamplesTable.previousWineSampleId eq previousWineSampleId.value }
            .empty()
    }

    override suspend fun findByCommissionIdAndPreviousWineSampleId(
        atom: Atom,
        commissionId: Commission.Id,
        previousWineSampleId: WineSample.Id
    ): WineSample? = AtomarixExposedAdapter.runWithAdapter(atom) {
        WineSamplesTable.selectAll()
            .andWhere { WineSamplesTable.commissionId eq commissionId.value }
            .andWhere { WineSamplesTable.previousWineSampleId eq previousWineSampleId.value }
            .singleOrNull()
            ?.let { WineSamplesTable.fromRow(it) }
    }

    override suspend fun filter(
        atom: Atom,
        commissionId: Commission.Id?,
        wineId: Wine.Id?,
        code: WineSample.Code?,
        previousWineSampleId: WineSample.Id?,
        pagination: Pagination
    ): List<WineSample> = AtomarixExposedAdapter.runWithAdapter(atom) {
        filterQuery(commissionId, wineId, code, previousWineSampleId)
            .limit(pagination.itemsPerPage).offset(pagination.offset)
            .map { WineSamplesTable.fromRow(it) }
    }

    override suspend fun count(atom: Atom, pagination: Pagination?): Long {
        TODO("Not yet implemented")
    }

    override suspend fun create(atom: Atom, entity: WineSample): WineSample = AtomarixExposedAdapter.runWithAdapter(atom) {
        WineSamplesTable.insert {
            WineSamplesTable.copy(entity, it)
        }.let { entity }
    }

    override suspend fun delete(atom: Atom, id: WineSample.Id) {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(atom: Atom, pagination: Pagination): List<WineSample> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(atom: Atom, id: WineSample.Id): WineSample? = AtomarixExposedAdapter.runWithAdapter(atom) {
        WineSamplesTable.selectAll()
            .andWhere { WineSamplesTable.id eq id.value }
            .singleOrNull()
            ?.let { WineSamplesTable.fromRow(it) }
    }

    override suspend fun isExisting(atom: Atom, id: WineSample.Id): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !WineSamplesTable.selectAll()
            .andWhere { WineSamplesTable.id eq id.value }
            .empty()
    }

    override suspend fun update(atom: Atom, entity: WineSample): WineSample? {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(atom: Atom, entity: WineSample): WineSample {
        TODO("Not yet implemented")
    }



    private fun filterQuery(
        commissionId: Commission.Id?,
        wineId: Wine.Id?,
        code: WineSample.Code?,
        previousWineSampleId: WineSample.Id?,
    ) = WineSamplesTable.selectAll()
        .let { if(commissionId != null) it.andWhere { WineSamplesTable.commissionId eq commissionId.value} else it }
        .let { if(wineId != null) it.andWhere { WineSamplesTable.wineId eq wineId.value} else it }
        .let { if(code != null) it.andWhere { WineSamplesTable.code eq code.value} else it }
        .let { if(previousWineSampleId != null) it.andWhere { WineSamplesTable.previousWineSampleId eq previousWineSampleId.value} else it }
}