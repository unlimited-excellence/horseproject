package application.usecase

import com.axus.winelore.model.entity.Commission
import domain.ports.repositories.CommissionRepository
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class GetCommissionRequest(
    val commissionId: Commission.Id
) {
    suspend fun execute(): Commission? {
        val commissionRepository: CommissionRepository by inject(CommissionRepository::class.java)

        return commissionRepository.findById(commissionId)
    }
}