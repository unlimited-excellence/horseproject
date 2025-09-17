package domain.ports.repositories

import com.axus.id.model.value.AUID
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.atomarix.AtomarixRepository
import eth.likespro.commons.models.Option
import org.unlimitedexcellence.horseproject.model.entity.Transaction
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker

interface TransactionRepository: AtomarixRepository<Transaction, Transaction.Id> {
    suspend fun filter(sender: Option<AUID?>, recipient: Option<AUID?>, ticker: Option<Ticker>): List<Transaction> = atomic { filter(this, sender, recipient, ticker) }
    suspend fun filter(atom: Atom, sender: Option<AUID?>, recipient: Option<AUID?>, ticker: Option<Ticker>): List<Transaction>

    suspend fun filterAndAccumulateAmount(sender: Option<AUID?>, recipient: Option<AUID?>, ticker: Option<Ticker>): Amount = atomic { filterAndAccumulateAmount(this, sender, recipient, ticker) }
    suspend fun filterAndAccumulateAmount(atom: Atom, sender: Option<AUID?>, recipient: Option<AUID?>, ticker: Option<Ticker>): Amount
}