package application.usecase

import com.axus.winelore.model.entity.WineSample
import domain.ports.repositories.WineSampleRepository
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class IsExistingWineSampleRequest(
    val id: WineSample.Id
) {
    suspend fun execute(): Boolean {
        val wineSampleRepository: WineSampleRepository by inject(WineSampleRepository::class.java)

        return wineSampleRepository.isExisting(id)
    }
}