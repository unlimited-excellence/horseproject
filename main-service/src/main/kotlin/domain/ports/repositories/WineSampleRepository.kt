package domain.ports.repositories

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.WineSample
import com.axus.winelore.model.entity.WineSample.Code
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository
import eth.likespro.commons.models.Pagination

interface WineSampleRepository : AtomarixRepository<WineSample, WineSample.Id> {
    suspend fun isExistingByCommissionIdAndCode(commissionId: Commission.Id, code: Code): Boolean = atomic { isExistingByCommissionIdAndCode(this, commissionId, code) }
    suspend fun isExistingByCommissionIdAndCode(atom: Atom, commissionId: Commission.Id, code: Code): Boolean

    suspend fun findByCommissionIdAndCode(commissionId: Commission.Id, code: Code): WineSample? = atomic { findByCommissionIdAndCode(this, commissionId, code) }
    suspend fun findByCommissionIdAndCode(atom: Atom, commissionId: Commission.Id, code: Code): WineSample?

    suspend fun isExistingByCommissionIdAndPreviousWineSampleId(commissionId: Commission.Id, previousWineSampleId: WineSample.Id): Boolean = atomic { isExistingByCommissionIdAndPreviousWineSampleId(this, commissionId, previousWineSampleId) }
    suspend fun isExistingByCommissionIdAndPreviousWineSampleId(atom: Atom, commissionId: Commission.Id, previousWineSampleId: WineSample.Id): Boolean

    suspend fun findByCommissionIdAndPreviousWineSampleId(commissionId: Commission.Id, previousWineSampleId: WineSample.Id): WineSample? = atomic { findByCommissionIdAndPreviousWineSampleId(this, commissionId, previousWineSampleId) }
    suspend fun findByCommissionIdAndPreviousWineSampleId(atom: Atom, commissionId: Commission.Id, previousWineSampleId: WineSample.Id): WineSample?

    suspend fun filter(commissionId: Commission.Id?, wineId: Wine.Id?, code: Code?, previousWineSampleId: WineSample.Id?, pagination: Pagination): List<WineSample> = atomic { filter(this, commissionId, wineId, code, previousWineSampleId, pagination) }
    suspend fun filter(atom: Atom, commissionId: Commission.Id?, wineId: Wine.Id?, code: Code?, previousWineSampleId: WineSample.Id?, pagination: Pagination): List<WineSample>
}