package application.usecase

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.CommissionParticipant
import domain.ports.repositories.AbstractCommissionRepository
import eth.likespro.commons.models.Pagination
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class GetCommissionsByParticipantRequest(
    val auid: AUID,
    val pagination: Pagination
) {
    suspend fun execute(): List<Pair<Commission, CommissionParticipant>> {
        val abstractCommissionRepository: AbstractCommissionRepository by inject(AbstractCommissionRepository::class.java)

        if(pagination.itemsPerPage > 1000)
            throw Pagination.TooManyItemsRequestedException("Too many items requested. Limit is 1000")

        return abstractCommissionRepository.findCommissionsByParticipant(auid, pagination)
    }
}