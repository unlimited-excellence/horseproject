package application.usecase

import com.axus.id.FullPermission
import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLorePermissionBuilder
import com.axus.winelore.model.InsufficientPermissionsException
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.WineSample
import com.axus.winelore.model.entity.WineSample.Code
import domain.ports.repositories.WineSampleRepository
import eth.likespro.atomarix.Atom.Companion.atomic
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class CreateWineSampleRequest(
    val commissionId: Commission.Id,
    val wineId: Wine.Id,
    val code: Code,
    var previousWineSampleId: WineSample.Id,
    val tokenId: Token.Id
) {
    suspend fun execute(): WineSample {
        val session: Session = get<Session>(Session::class.java).refreshedIfExpired()
        val wineSampleRepository: WineSampleRepository by inject(WineSampleRepository::class.java)

        val commission = GetCommissionRequest(commissionId).execute() ?: throw Commission.Id.IsInvalidException("Commission with the specified Id not found")
        if(commission.startedAt != null)
            throw Commission.IsAlreadyStartedException()

        val competition = GetCompetitionRequest(commission.competitionId).execute() ?: throw Competition.Id.IsInvalidException("Competition with the specified Id not found")

        if(!session.hasTokenPermission(tokenId.value, FullPermission(WineLorePermissionBuilder.createWineSample(AUID(session.auid), competition.organizer, commissionId))))
            throw InsufficientPermissionsException()

        val wineSample = WineSample(
            commissionId = commissionId,
            wineId = wineId,
            code = code,
            previousWineSampleId = previousWineSampleId,
        )
        
        if(!IsExistingWineRequest(wineId).execute())
            throw Wine.Id.IsInvalidException("Wine with specified Id not found")
        return atomic {
            if(wineSampleRepository.isExistingByCommissionIdAndCode(this, commissionId, code))
                throw WineSample.CompetitionIdAndCodeAreAlreadyUsedException()
            if(previousWineSampleId != WineSample.Id.NONE && !wineSampleRepository.isExisting(this, previousWineSampleId))
                throw WineSample.Id.IsInvalidException("WineSample with specified Id of previousWineSampleId not found")
            if(wineSampleRepository.isExistingByCommissionIdAndPreviousWineSampleId(this, commissionId, previousWineSampleId))
                throw WineSample.CompetitionIdAndPreviousWineSampleIdAreAlreadyUsedException()
            wineSampleRepository.create(this, wineSample)
        }
    }
}