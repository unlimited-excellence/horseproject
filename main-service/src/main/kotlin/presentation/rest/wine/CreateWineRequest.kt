package presentation.rest.wine

import com.axus.winelore.model.entity.Wine
import kotlinx.serialization.Serializable

@Serializable
data class CreateWineRequest(
    val producer: Long,
    val name: String,
    val iteration: Long,
    val color: Wine.Color,
    val type: Wine.Type,
    val tokenId: String
)