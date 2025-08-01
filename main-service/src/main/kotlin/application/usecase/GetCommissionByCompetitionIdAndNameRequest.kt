package application.usecase

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import domain.ports.repositories.CommissionRepository
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class GetCommissionByCompetitionIdAndNameRequest(
    val competitionId: Competition.Id,
    val name: Commission.Name
) {
    suspend fun execute(): Commission? {
        val commissionRepository: CommissionRepository by inject(CommissionRepository::class.java)

        return commissionRepository.findByCompetitionIdAndName(competitionId, name)
    }
}