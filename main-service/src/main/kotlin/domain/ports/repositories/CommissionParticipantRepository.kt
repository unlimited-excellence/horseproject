package domain.ports.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.CommissionParticipant
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository
import eth.likespro.commons.models.Pagination

interface CommissionParticipantRepository : AtomarixRepository<CommissionParticipant, CommissionParticipant.Id> {
    suspend fun isExistingByCommissionIdAndAUID(commissionId: Commission.Id, auid: AUID): Boolean = atomic { isExistingByCommissionIdAndAUID(this, commissionId, auid) }
    suspend fun isExistingByCommissionIdAndAUID(atom: Atom, commissionId: Commission.Id, auid: AUID): Boolean

    suspend fun findByCommissionIdAndAUID(commissionId: Commission.Id, auid: AUID): CommissionParticipant? = atomic { findByCommissionIdAndAUID(this, commissionId, auid) }
    suspend fun findByCommissionIdAndAUID(atom: Atom, commissionId: Commission.Id, auid: AUID): CommissionParticipant?

    suspend fun filter(commissionId: Commission.Id?, auid: AUID?, role: CommissionParticipant.Role?, pagination: Pagination): List<CommissionParticipant> = atomic { filter(this, commissionId, auid, role, pagination) }
    suspend fun filter(atom: Atom, commissionId: Commission.Id?, auid: AUID?, role: CommissionParticipant.Role?, pagination: Pagination): List<CommissionParticipant>
}