package infrastructure.persistence.repositories

import com.axus.id.model.value.AUID
import domain.ports.repositories.TransactionRepository
import domain.ports.repositories.UserBalanceAbstractRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Option
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker

class UserBalanceAbstractRepositoryImpl(
    private val transactionRepository: TransactionRepository
) : UserBalanceAbstractRepository {
    override suspend fun getUserBalance(atom: Atom, auid: AUID, ticker: Ticker): Amount = AtomarixExposedAdapter.runWithAdapter(atom) {
        transactionRepository.filterAndAccumulateAmount(
            atom,
            Option.None,
            Option.Some(auid),
            Option.Some(ticker)
        ) - transactionRepository.filterAndAccumulateAmount(
            atom,
            Option.Some(auid),
            Option.None,
            Option.Some(ticker)
        )
    }
}