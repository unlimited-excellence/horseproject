package application.usecase

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.WineSample
import domain.ports.repositories.WineSampleRepository
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class GetWineSampleByCommissionIdAndCodeRequest(
    val commissionId: Commission.Id,
    val code: WineSample.Code
) {
    suspend fun execute(): WineSample? {
        val wineSampleRepository: WineSampleRepository by inject(WineSampleRepository::class.java)

        return wineSampleRepository.findByCommissionIdAndCode(commissionId, code)
    }
}