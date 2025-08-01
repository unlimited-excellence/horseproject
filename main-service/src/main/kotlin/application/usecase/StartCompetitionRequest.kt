package application.usecase

import com.axus.id.FullPermission
import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLorePermissionBuilder
import com.axus.winelore.model.InsufficientPermissionsException
import com.axus.winelore.model.entity.Competition
import domain.ports.repositories.CompetitionRepository
import eth.likespro.atomarix.Atom.Companion.atomic
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class StartCompetitionRequest(
    val competitionId: Competition.Id,
    val tokenId: Token.Id,
) {
    suspend fun execute() {
        TODO("Not yet implemented correctly")
        val session: Session by inject(Session::class.java)
        val competitionRepository: CompetitionRepository by inject(CompetitionRepository::class.java)

        val competition = GetCompetitionRequest(competitionId).execute()
            ?: throw Competition.Id.IsInvalidException("Competition with the specified Id not found")

        if(!session.hasTokenPermission(tokenId.value, FullPermission(WineLorePermissionBuilder.startCompetition(AUID(session.auid), competition.organizer, competitionId))))
            throw InsufficientPermissionsException()

        return atomic {
            val actualCompetition = competitionRepository.findById(this, competitionId)!!
            if(actualCompetition.status != Competition.Status.APPROVED)
            throw Competition.Status.IsInvalidException("Competition.Status must be APPROVED to start the competition.")
            competitionRepository.updateStatus(this, competitionId, Competition.Status.STARTED)
        }
    }
}