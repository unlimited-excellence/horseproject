package application.usecase

import com.axus.id.FullPermission
import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLorePermissionBuilder
import com.axus.winelore.model.InsufficientPermissionsException
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.Competition.Name
import domain.ports.repositories.CompetitionRepository
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class CreateCompetitionRequest(
    val organizer: AUID,
    val name: Name,
    var plannedStartAt: Timestamp,
    val tokenId: Token.Id
) {
    suspend fun execute(): Competition {
        val session: Session = get<Session>(Session::class.java).refreshedIfExpired()
        val competitionRepository: CompetitionRepository by inject(CompetitionRepository::class.java)

        if(!session.hasTokenPermission(tokenId.value, FullPermission(WineLorePermissionBuilder.createCompetition(AUID(session.auid), organizer))))
            throw InsufficientPermissionsException()

        val competition = Competition(
            organizer = organizer,
            name = name,
            plannedStartAt = plannedStartAt,
        )

        return atomic {
            if(competitionRepository.isExistingByName(this, name))
                throw Competition.NameIsAlreadyUsedException()
            competitionRepository.create(this, competition)
        }
    }
}