package application.usecase

import app.logger
import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.model.InsufficientPermissionsException
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import com.axus.winelore.model.entity.WineSample
import domain.ports.repositories.CommissionRepository
import domain.ports.repositories.WineSampleRepository
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class StartCommissionRequest(
    val auid: AUID,
    val commissionId: Commission.Id,
    val tokenId: Token.Id,
) {
    suspend fun execute() {
        val session: Session = get<Session>(Session::class.java).refreshedIfExpired()
        val commissionRepository: CommissionRepository by inject(CommissionRepository::class.java)
        val wineSampleRepository: WineSampleRepository by inject(WineSampleRepository::class.java) // Services hard connection, TODO rewrite
// TODO Add auth check by token
        val participant = GetCommissionParticipantByCommissionIdAndAUIDRequest(commissionId, auid).execute()

        if(participant?.role != CommissionParticipant.Role.HEAD_OF_EXPERTS)
            throw InsufficientPermissionsException()

        return atomic {
            val commission = commissionRepository.findById(this, commissionId)
                ?: throw Commission.Id.IsInvalidException("Commission with the specified Id not found")

            if(!commission.isApproved)
                throw Commission.IsNotApprovedException()
            if(commission.startedAt != null)
                throw Commission.IsAlreadyStartedException()

            val currentWineSample = wineSampleRepository.findByCommissionIdAndPreviousWineSampleId(this, commissionId, WineSample.Id.NONE)
                ?: throw Commission.NoWineSamplesException()

            commissionRepository.updateStartedAtAndCurrentWineSampleId(this, commissionId, Timestamp.now(), currentWineSample.id)
        }
    }
}