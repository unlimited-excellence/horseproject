package application.usecase

import com.axus.winelore.model.entity.Competition
import domain.ports.repositories.CompetitionRepository
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class GetCompetitionRequest(
    val id: Competition.Id
) {
    suspend fun execute(): Competition? {
        val competitionRepository: CompetitionRepository by inject(CompetitionRepository::class.java)

        return competitionRepository.findById(id)
    }
}