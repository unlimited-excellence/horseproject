package presentation.rest.dto

import com.axus.winelore.model.entity.CommissionParticipant
import com.axus.winelore.model.entity.CommissionParticipant.Role
import kotlinx.serialization.Serializable

@Serializable
data class CommissionParticipantDTO(
    val id: String,
    val commissionId: String,
    val auid: Long,
    val role: Role
) {
    constructor(commissionParticipant: CommissionParticipant) : this(
        id = commissionParticipant.id.value,
        commissionId = commissionParticipant.commissionId.value,
        auid = commissionParticipant.auid.value,
        role = commissionParticipant.role
    )
}