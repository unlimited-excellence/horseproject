package presentation.rest.commissionparticipant

import com.axus.winelore.model.entity.CommissionParticipant
import eth.likespro.commons.models.Pagination
import kotlinx.serialization.Serializable

@Serializable
data class FilterCommissionParticipantsRequest(
    val commissionId: String?,
    val auid: Long?,
    val role: CommissionParticipant.Role?,
    val pagination: Pagination
)