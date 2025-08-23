package domain.ports.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.commons.models.Pagination

interface AbstractCommissionRepository {
    suspend fun findCommissionsByParticipant(auid: AUID, pagination: Pagination): List<kotlin.Pair<Commission, CommissionParticipant>> = atomic { findCommissionsByParticipant(this, auid, pagination) }
    suspend fun findCommissionsByParticipant(atom: Atom, auid: AUID, pagination: Pagination): List<kotlin.Pair<Commission, CommissionParticipant>>
}