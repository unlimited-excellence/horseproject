package application.usecase

import com.axus.id.model.value.AUID
import domain.ports.repositories.UserBalanceAbstractRepository
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker

@Serializable
data class FetchUserBalanceRequest(
    val auid: AUID,
    val ticker: Ticker
) {
    suspend fun execute(): Amount {
        val userBalanceAbstractRepository: UserBalanceAbstractRepository by inject(UserBalanceAbstractRepository::class.java)

        return userBalanceAbstractRepository.getUserBalance(auid, ticker)
    }
}