package presentation.rest.commissionparticipant

import com.axus.winelore.model.entity.CommissionParticipant
import kotlinx.serialization.Serializable

@Serializable
data class GetCommissionParticipantByCommissionIdAndAUIDRequest(
    val commissionId: String,
    val auid: Long
)