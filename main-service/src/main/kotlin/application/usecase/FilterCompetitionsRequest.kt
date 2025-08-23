package application.usecase

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Competition
import domain.ports.repositories.CompetitionRepository
import eth.likespro.commons.models.Pagination
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class FilterCompetitionsRequest(
    val organizer: AUID?,
    val pagination: Pagination
) {
    suspend fun execute(): List<Competition> {
        val competitionRepository: CompetitionRepository by inject(CompetitionRepository::class.java)

        if(pagination.itemsPerPage > 1000)
            throw Pagination.TooManyItemsRequestedException("Too many items requested. Limit is 1000")

        return competitionRepository.filter(organizer, pagination)
    }
}