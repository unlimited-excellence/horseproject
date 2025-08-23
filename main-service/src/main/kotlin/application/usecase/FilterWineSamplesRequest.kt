package application.usecase

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.WineSample
import com.axus.winelore.model.entity.WineSample.Code
import domain.ports.repositories.WineSampleRepository
import eth.likespro.commons.models.Pagination
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class FilterWineSamplesRequest(
    val commissionId: Commission.Id?,
    val wineId: Wine.Id?,
    val code: Code?,
    val previousWineSampleId: WineSample.Id?,
    val pagination: Pagination
) {
    suspend fun execute(): List<WineSample> {
        val wineSampleRepository: WineSampleRepository by inject(WineSampleRepository::class.java)

        if(pagination.itemsPerPage > 1000)
            throw Pagination.TooManyItemsRequestedException("Too many items requested. Limit is 1000")

        return wineSampleRepository.filter(commissionId, wineId, code, previousWineSampleId, pagination)
    }
}