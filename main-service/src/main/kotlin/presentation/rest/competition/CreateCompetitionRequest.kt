package presentation.rest.competition

import kotlinx.serialization.Serializable

@Serializable
data class CreateCompetitionRequest(
    val organizer: Long,
    val name: String,
    var plannedStartAt: Long,
    val tokenId: String
)