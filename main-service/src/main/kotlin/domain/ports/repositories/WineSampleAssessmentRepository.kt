package domain.ports.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.WineSample
import com.axus.winelore.model.entity.WineSampleAssessment
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository

interface WineSampleAssessmentRepository : AtomarixRepository<WineSampleAssessment, WineSampleAssessment.Id> {
    suspend fun isExistingByCommissionIdAndAUIDAndWineSampleId(
        commissionId: Commission.Id,
        from: AUID,
        wineSampleId: WineSample.Id
    ): Boolean = atomic { isExistingByCommissionIdAndAUIDAndWineSampleId(this, commissionId, from, wineSampleId) }
    suspend fun isExistingByCommissionIdAndAUIDAndWineSampleId(
        atom: Atom,
        commissionId: Commission.Id,
        from: AUID,
        wineSampleId: WineSample.Id
    ): Boolean

    suspend fun findByCommissionIdAndAUIDAndWineSampleId(
        commissionId: Commission.Id,
        from: AUID,
        wineSampleId: WineSample.Id
    ): WineSampleAssessment? = atomic { findByCommissionIdAndAUIDAndWineSampleId(this, commissionId, from, wineSampleId) }
    suspend fun findByCommissionIdAndAUIDAndWineSampleId(
        atom: Atom,
        commissionId: Commission.Id,
        from: AUID,
        wineSampleId: WineSample.Id
    ): WineSampleAssessment?
}