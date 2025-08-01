package application.usecase

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Wine
import domain.ports.repositories.WineRepository
import eth.likespro.commons.models.value.Iteration
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class GetWineByProducerAndNameAndIterationRequest(
    val producer: AUID,
    val name: Wine.Name,
    val iteration: Iteration
) {
    suspend fun execute(): Wine? {
        val wineRepository: WineRepository by inject(WineRepository::class.java)

        return wineRepository.findByProducerAndNameAndIteration(producer, name, iteration)
    }
}