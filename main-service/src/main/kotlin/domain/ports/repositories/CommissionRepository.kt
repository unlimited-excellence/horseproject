package domain.ports.repositories

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.WineSample
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository
import eth.likespro.commons.models.value.Timestamp

interface CommissionRepository : AtomarixRepository<Commission, Commission.Id> {
    suspend fun findByCompetitionId(competitionId: Competition.Id): List<Commission> = atomic { findByCompetitionId(this, competitionId) }
    suspend fun findByCompetitionId(atom: Atom, competitionId: Competition.Id): List<Commission>

    suspend fun findByCompetitionIdAndName(competitionId: Competition.Id, name: Commission.Name): Commission? = atomic { findByCompetitionIdAndName(this, competitionId, name) }
    suspend fun findByCompetitionIdAndName(atom: Atom, competitionId: Competition.Id, name: Commission.Name): Commission?

    suspend fun isExistingByCompetitionIdAndName(competitionId: Competition.Id, name: Commission.Name): Boolean = atomic { isExistingByCompetitionIdAndName(this, competitionId, name) }
    suspend fun isExistingByCompetitionIdAndName(atom: Atom, competitionId: Competition.Id, name: Commission.Name): Boolean

    suspend fun updateStartedAtAndCurrentWineSampleId(id: Commission.Id, startedAt: Timestamp?, currentWineSampleId: WineSample.Id) = atomic { updateStartedAtAndCurrentWineSampleId(this, id, startedAt, currentWineSampleId) }
    suspend fun updateStartedAtAndCurrentWineSampleId(atom: Atom, id: Commission.Id, startedAt: Timestamp?, currentWineSampleId: WineSample.Id)

    suspend fun updateCurrentWineSampleId(id: Commission.Id, currentWineSampleId: Wine.Id) = atomic { updateCurrentWineSampleId(this, id, currentWineSampleId) }
    suspend fun updateCurrentWineSampleId(atom: Atom, id: Commission.Id, currentWineSampleId: Wine.Id)
}