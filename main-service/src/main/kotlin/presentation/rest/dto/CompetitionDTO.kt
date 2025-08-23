package presentation.rest.dto

import com.axus.winelore.model.entity.Competition
import kotlinx.serialization.Serializable

@Serializable
data class CompetitionDTO(
    val id: String,
    val organizer: Long,
    val name: String,
    val plannedStartAt: Long
) {
    constructor(competition: Competition) : this(
        id = competition.id.value,
        organizer = competition.organizer.value,
        name = competition.name.value,
        plannedStartAt = competition.plannedStartAt.value
    )
}