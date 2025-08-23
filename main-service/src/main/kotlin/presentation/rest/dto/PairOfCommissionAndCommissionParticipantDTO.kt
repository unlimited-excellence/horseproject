package presentation.rest.dto

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import com.axus.winelore.model.entity.CommissionParticipant.Role
import kotlinx.serialization.Serializable

@Serializable
data class PairOfCommissionAndCommissionParticipantDTO(
    val commissionId: String,
    val competitionId: String,
    val name: String,
    var isApproved: Boolean,
    var currentWineSampleId: String?,
    var startedAt: Long? = null,
    val endedAt: Long? = null,
    val participantId: String,
    val auid: Long,
    val role: Role
) {
    constructor(pairOfCommissionAndCommissionParticipant: Pair<Commission, CommissionParticipant>) : this(
        commissionId = pairOfCommissionAndCommissionParticipant.second.commissionId.value,
        competitionId = pairOfCommissionAndCommissionParticipant.first.competitionId.value,
        name = pairOfCommissionAndCommissionParticipant.first.name.value,
        isApproved = pairOfCommissionAndCommissionParticipant.first.isApproved,
        currentWineSampleId = pairOfCommissionAndCommissionParticipant.first.currentWineSampleId?.value,
        startedAt = pairOfCommissionAndCommissionParticipant.first.startedAt?.value,
        endedAt = pairOfCommissionAndCommissionParticipant.first.endedAt?.value,
        participantId = pairOfCommissionAndCommissionParticipant.second.id.value,
        auid = pairOfCommissionAndCommissionParticipant.second.auid.value,
        role = pairOfCommissionAndCommissionParticipant.second.role
    )
}