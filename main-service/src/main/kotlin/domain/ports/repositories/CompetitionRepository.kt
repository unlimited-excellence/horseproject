package domain.ports.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.Competition
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository
import eth.likespro.commons.models.Pagination

interface CompetitionRepository : AtomarixRepository<Competition, Competition.Id> {
    suspend fun isExistingByName(name: Competition.Name): Boolean = atomic { isExistingByName(this, name) }
    suspend fun isExistingByName(atom: Atom, name: Competition.Name): Boolean

    suspend fun findByName(name: Competition.Name): Competition? = atomic { findByName(this, name) }
    suspend fun findByName(atom: Atom, name: Competition.Name): Competition?

    suspend fun filter(organizer: AUID?, pagination: Pagination): List<Competition> = atomic { filter(this, organizer, pagination) }
    suspend fun filter(atom: Atom, organizer: AUID?, pagination: Pagination): List<Competition>
}