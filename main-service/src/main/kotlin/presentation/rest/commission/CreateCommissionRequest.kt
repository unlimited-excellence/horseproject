package presentation.rest.commission

import kotlinx.serialization.Serializable

@Serializable
data class CreateCommissionRequest(
    val competitionId: String,
    val name: String,
    val tokenId: String,
)