package domain.ports.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Wine
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository
import eth.likespro.commons.models.value.Iteration

interface WineRepository: AtomarixRepository<Wine, Wine.Id>{
    suspend fun isExistingByProducerAndNameAndIteration(producer: AUID, name: Wine.Name, iteration: Iteration): Boolean = atomic { isExistingByProducerAndNameAndIteration(this, producer, name, iteration) }
    suspend fun isExistingByProducerAndNameAndIteration(atom: Atom, producer: AUID, name: Wine.Name, iteration: Iteration): Boolean

    suspend fun findByProducerAndNameAndIteration(producer: AUID, name: Wine.Name, iteration: Iteration): Wine? = atomic { findByProducerAndNameAndIteration(this, producer, name, iteration) }
    suspend fun findByProducerAndNameAndIteration(atom: Atom, producer: AUID, name: Wine.Name, iteration: Iteration): Wine?
}