package presentation.rest.competition

import eth.likespro.commons.models.Pagination
import kotlinx.serialization.Serializable

@Serializable
data class FilterCompetitionsRequest(
    val organizer: Long?,
    val pagination: Pagination
)