package application.usecase

import com.axus.id.FullPermission
import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLorePermissionBuilder
import com.axus.winelore.model.CommissionCompetitionIdAndNameAreAlreadyUsedException
import com.axus.winelore.model.InsufficientPermissionsException
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Commission.Name
import com.axus.winelore.model.entity.Competition
import domain.ports.repositories.CommissionRepository
import eth.likespro.atomarix.Atom.Companion.atomic
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class CreateCommissionRequest(
    val competitionId: Competition.Id,
    val name: Name,
    val tokenId: Token.Id,
) {
    suspend fun execute(): Commission {
        val session: Session by inject(Session::class.java)
        val commissionRepository: CommissionRepository by inject(CommissionRepository::class.java)

        val competition = GetCompetitionRequest(competitionId).execute() ?: throw Competition.Id.IsInvalidException("Competition with the specified Id not found")

        if(!session.hasTokenPermission(tokenId.value, FullPermission(WineLorePermissionBuilder.createCommission(AUID(session.auid), competition.organizer, competitionId))))
            throw InsufficientPermissionsException()

        val commission = Commission(
            competitionId = competitionId,
            name = name,
        )

        return atomic {
            if(commissionRepository.isExistingByCompetitionIdAndName(competitionId, name))
                throw CommissionCompetitionIdAndNameAreAlreadyUsedException()
            commissionRepository.create(commission)
        }
    }
}