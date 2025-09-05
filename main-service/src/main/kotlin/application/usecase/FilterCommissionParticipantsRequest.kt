package application.usecase

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import domain.ports.repositories.CommissionParticipantRepository
import eth.likespro.commons.models.Pagination
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class FilterCommissionParticipantsRequest(
    val commissionId: Commission.Id?,
    val auid: AUID?,
    val role: CommissionParticipant.Role?,
    val pagination: Pagination
) {
    suspend fun execute(): List<CommissionParticipant> {
        val commissionParticipantRepository: CommissionParticipantRepository by inject(CommissionParticipantRepository::class.java)

        if(pagination.itemsPerPage > 1000)
            throw Pagination.TooManyItemsRequestedException("Too many items requested. Limit is 1000")

        return commissionParticipantRepository.filter(commissionId, auid, role, pagination)
    }
}