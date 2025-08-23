package presentation.rest.commissionparticipant

import com.axus.id.model.value.AUID
import eth.likespro.commons.models.Pagination
import kotlinx.serialization.Serializable

@Serializable
data class GetCommissionsByParticipantRequest(
    val auid: Long,
    val pagination: Pagination
)