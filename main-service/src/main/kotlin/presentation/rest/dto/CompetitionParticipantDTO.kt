package presentation.rest.dto

import com.axus.winelore.model.entity.CommissionParticipant
import com.axus.winelore.model.entity.CommissionParticipant.Role
import kotlinx.serialization.Serializable

@Serializable
data class CompetitionParticipantDTO(
    val id: String,
    val commissionId: String,
    val auid: Long,
    val role: Role
) {
    constructor(competitionParticipant: CommissionParticipant) : this(
        id = competitionParticipant.id.value,
        commissionId = competitionParticipant.commissionId.value,
        auid = competitionParticipant.auid.value,
        role = competitionParticipant.role
    )
}