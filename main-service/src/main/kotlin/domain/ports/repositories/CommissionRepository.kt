package domain.ports.repositories

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository

interface CommissionRepository : AtomarixRepository<Commission, Commission.Id> {
    suspend fun findByCompetitionId(competitionId: Competition.Id): List<Commission> = atomic { findByCompetitionId(this, competitionId) }
    suspend fun findByCompetitionId(atom: Atom, competitionId: Competition.Id): List<Commission>

    suspend fun findByCompetitionIdAndName(competitionId: Competition.Id, name: Commission.Name): Commission? = atomic { findByCompetitionIdAndName(this, competitionId, name) }
    suspend fun findByCompetitionIdAndName(atom: Atom, competitionId: Competition.Id, name: Commission.Name): Commission?

    suspend fun isExistingByCompetitionIdAndName(competitionId: Competition.Id, name: Commission.Name): Boolean = atomic { isExistingByCompetitionIdAndName(this, competitionId, name) }
    suspend fun isExistingByCompetitionIdAndName(atom: Atom, competitionId: Competition.Id, name: Commission.Name): Boolean
}