package presentation.rest.dto

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Commission.Id
import com.axus.winelore.model.entity.Commission.Name
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.WineSample
import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class CommissionDTO(
    val id: String,
    val competitionId: String,
    val name: String,
    var isApproved: Boolean,
    var currentWineSampleId: String,
    var startedAt: Long?,
    val endedAt: Long?,
) {
    constructor(commission: Commission) : this(
        id = commission.id.value.toString(),
        competitionId = commission.competitionId.value.toString(),
        name = commission.name.value,
        isApproved = commission.isApproved,
        currentWineSampleId = commission.currentWineSampleId?.value?.toString() ?: "",
        startedAt = commission.startedAt?.value,
        endedAt = commission.endedAt?.value,
    )
}