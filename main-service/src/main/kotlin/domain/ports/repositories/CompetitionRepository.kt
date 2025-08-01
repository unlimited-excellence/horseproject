package domain.ports.repositories

import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.Competition
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository

interface CompetitionRepository : AtomarixRepository<Competition, Competition.Id> {
    suspend fun isExistingByName(name: Competition.Name): Boolean = atomic { isExistingByName(this, name) }
    suspend fun isExistingByName(atom: Atom, name: Competition.Name): Boolean

    suspend fun findByName(name: Competition.Name): Competition? = atomic { findByName(this, name) }
    suspend fun findByName(atom: Atom, name: Competition.Name): Competition?

    suspend fun updateStatus(id: Competition.Id, status: Competition.Status) = atomic { updateStatus(this, id, status) }
    suspend fun updateStatus(atom: Atom, id: Competition.Id, status: Competition.Status)

    suspend fun updateCurrentWineId(id: Competition.Id, currentWine: Wine.Id) = atomic { updateCurrentWineId(this, id, currentWine) }
    suspend fun updateCurrentWineId(atom: Atom, id: Competition.Id, currentWine: Wine.Id)
}