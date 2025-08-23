package application.usecase

import com.axus.id.FullPermission
import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLorePermissionBuilder
import com.axus.winelore.model.InsufficientPermissionsException
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import com.axus.winelore.model.entity.WineSample
import com.axus.winelore.model.entity.WineSampleAssessment
import com.axus.winelore.model.entity.WineSampleAssessment.Mark
import domain.ports.repositories.WineSampleAssessmentRepository
import eth.likespro.atomarix.Atom.Companion.atomic
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.json.JSONObject
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class CreateWineSampleAssessmentRequest(
    val commissionId: Commission.Id,
    val from: AUID,
    val wineSampleId: WineSample.Id,
    val mark: Mark,
    @Contextual
    val metadata: JSONObject = JSONObject(),
    val tokenId: Token.Id,
) {
    suspend fun execute(): WineSampleAssessment {
        val session: Session = get<Session>(Session::class.java).refreshedIfExpired()
        val wineSampleAssessmentRepository: WineSampleAssessmentRepository by inject(WineSampleAssessmentRepository::class.java)

        if(!session.hasTokenPermission(tokenId.value, FullPermission(WineLorePermissionBuilder.createWineSampleAssessment(AUID(session.auid), from, commissionId))))
            throw InsufficientPermissionsException()

        if(!IsExistingCommissionParticipantByCommissionIdAndAUIDRequest(commissionId, from).execute())
            throw CommissionParticipant.IsUnavailableException()

        val commission = GetCommissionRequest(commissionId).execute()
            ?: throw Commission.Id.IsInvalidException("Commission with the specified Id not found")
        if(commission.startedAt == null)
            throw Commission.IsNotStartedException()
        if(commission.endedAt != null)
            throw Commission.IsAlreadyEndedException()

        val wineSample = GetWineSampleRequest(wineSampleId).execute()
            ?: throw WineSample.Id.IsInvalidException("WineSample with the specified Id not found")

        val wineSampleAssessment = WineSampleAssessment(
            commissionId = commissionId,
            from = from,
            wineSampleId = wineSampleId,
            mark = mark,
            metadata = metadata
        )

        return atomic {
            if(wineSampleAssessmentRepository.isExistingByCommissionIdAndAUIDAndWineSampleId(this, commissionId, from, wineSampleId))
                throw WineSampleAssessment.CommissionIdAndAUIDAndWineSampleIdAreAlreadyUsedException()

            wineSampleAssessmentRepository.create(this, wineSampleAssessment)
        }
    }
}