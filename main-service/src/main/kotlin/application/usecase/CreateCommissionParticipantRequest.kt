package application.usecase

import com.axus.id.FullPermission
import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLorePermissionBuilder
import com.axus.winelore.model.InsufficientPermissionsException
import com.axus.winelore.model.CommissionParticipantCompetitionIdAndAUIDAreAlreadyUsedException
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.CommissionParticipant
import com.axus.winelore.model.entity.CommissionParticipant.Role
import domain.ports.repositories.CommissionParticipantRepository
import eth.likespro.atomarix.Atom.Companion.atomic
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class CreateCommissionParticipantRequest(
    val commissionId: Commission.Id,
    val auid: AUID,
    val role: Role,
    val tokenId: Token.Id,
) {
    suspend fun execute(): CommissionParticipant {
        val session: Session by inject(Session::class.java)
        val commissionParticipantRepository: CommissionParticipantRepository by inject(CommissionParticipantRepository::class.java)

        val commission = GetCommissionRequest(commissionId).execute() ?: throw Commission.Id.IsInvalidException("Commission with the specified Id not found")

        val competition = GetCompetitionRequest(commission.competitionId).execute()
            ?: throw Competition.Id.IsInvalidException("Competition with the specified Id not found")

        if(!session.hasTokenPermission(tokenId.value, FullPermission(WineLorePermissionBuilder.createCommissionParticipant(AUID(session.auid), competition.organizer, commissionId, role))))
            throw InsufficientPermissionsException()

        val commissionParticipant = CommissionParticipant(
            commissionId = commissionId,
            auid = auid,
            role = role,
        )

        return atomic {
            if(commissionParticipantRepository.isExistingByCommissionIdAndAUID(this, commissionId, auid))
                throw CommissionParticipantCompetitionIdAndAUIDAreAlreadyUsedException()
            commissionParticipantRepository.create(this, commissionParticipant)
        }
    }
}