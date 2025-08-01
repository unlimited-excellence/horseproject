package application.usecase

import com.axus.winelore.model.entity.Wine
import domain.ports.repositories.WineRepository
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class IsExistingWineRequest(
    val id: Wine.Id
) {
    suspend fun execute(): Boolean {
        val wineRepository: WineRepository by inject(WineRepository::class.java)

        return wineRepository.isExisting(id)
    }
}