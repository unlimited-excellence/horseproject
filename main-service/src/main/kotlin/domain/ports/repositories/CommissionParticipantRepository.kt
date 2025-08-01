package domain.ports.repositories

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.CommissionParticipant
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository

interface CommissionParticipantRepository : AtomarixRepository<CommissionParticipant, CommissionParticipant.Id> {
    suspend fun isExistingByCommissionIdAndAUID(commissionId: Commission.Id, auid: AUID): Boolean = atomic { isExistingByCommissionIdAndAUID(this, commissionId, auid) }
    suspend fun isExistingByCommissionIdAndAUID(atom: Atom, commissionId: Commission.Id, auid: AUID): Boolean

    suspend fun findByCommissionIdAndAUID(commissionId: Commission.Id, auid: AUID): CommissionParticipant? = atomic { findByCommissionIdAndAUID(this, commissionId, auid) }
    suspend fun findByCommissionIdAndAUID(atom: Atom, commissionId: Commission.Id, auid: AUID): CommissionParticipant?
}