package presentation.rest.commission

import kotlinx.serialization.Serializable

@Serializable
data class StartCommissionRequest(
    val auid: Long,
    val commissionId: String,
    val tokenId: String,
)