package infrastructure.persistence.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.WineSample
import com.axus.winelore.model.entity.WineSampleAssessment
import domain.ports.repositories.WineSampleAssessmentRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Pagination
import infrastructure.persistence.tables.WineSampleAssessmentsTable
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class WineSampleAssessmentRepositoryImpl : WineSampleAssessmentRepository {
    override suspend fun isExistingByCommissionIdAndAUIDAndWineSampleId(
        atom: Atom,
        commissionId: Commission.Id,
        from: AUID,
        wineSampleId: WineSample.Id
    ): Boolean = AtomarixExposedAdapter.runWithAdapter(atom) {
        !WineSampleAssessmentsTable.selectAll()
            .andWhere { WineSampleAssessmentsTable.commissionId eq commissionId.value }
            .andWhere { WineSampleAssessmentsTable.from eq from.value }
            .andWhere { WineSampleAssessmentsTable.wineSampleId eq wineSampleId.value }
            .empty()
    }

    override suspend fun findByCommissionIdAndAUIDAndWineSampleId(
        atom: Atom,
        commissionId: Commission.Id,
        from: AUID,
        wineSampleId: WineSample.Id
    ): WineSampleAssessment? = AtomarixExposedAdapter.runWithAdapter(atom) {
        WineSampleAssessmentsTable.selectAll()
            .andWhere { WineSampleAssessmentsTable.commissionId eq commissionId.value }
            .andWhere { WineSampleAssessmentsTable.from eq from.value }
            .andWhere { WineSampleAssessmentsTable.wineSampleId eq wineSampleId.value }
            .singleOrNull()
            ?.let { WineSampleAssessmentsTable.fromRow(it) }
    }

    override suspend fun count(atom: Atom, pagination: Pagination?): Long {
        TODO("Not yet implemented")
    }

    override suspend fun create(atom: Atom, entity: WineSampleAssessment): WineSampleAssessment = AtomarixExposedAdapter.runWithAdapter(atom) {
        WineSampleAssessmentsTable.insert {
            WineSampleAssessmentsTable.copy(entity, it)
        }.let { entity }
    }

    override suspend fun delete(atom: Atom, id: WineSampleAssessment.Id) {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(atom: Atom, pagination: Pagination): List<WineSampleAssessment> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(atom: Atom, id: WineSampleAssessment.Id): WineSampleAssessment? = AtomarixExposedAdapter.runWithAdapter(atom) {
        WineSampleAssessmentsTable.selectAll()
            .where { WineSampleAssessmentsTable.id eq id.value }
            .singleOrNull()
            ?.let { WineSampleAssessmentsTable.fromRow(it) }
    }

    override suspend fun isExisting(atom: Atom, id: WineSampleAssessment.Id): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(atom: Atom, entity: WineSampleAssessment): WineSampleAssessment? {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(atom: Atom, entity: WineSampleAssessment): WineSampleAssessment {
        TODO("Not yet implemented")
    }
// дякую
}