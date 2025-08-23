package application.usecase

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import domain.ports.repositories.CommissionParticipantRepository
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class IsExistingCommissionParticipantByCommissionIdAndAUIDRequest(
    val commissionId: Commission.Id,
    val auid: AUID,
) {
    suspend fun execute(): Boolean {
        val commissionParticipantRepository: CommissionParticipantRepository by inject(CommissionParticipantRepository::class.java)

        return commissionParticipantRepository.isExistingByCommissionIdAndAUID(commissionId, auid)
    }
}