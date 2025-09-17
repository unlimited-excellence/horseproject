package domain.ports.repositories

import com.axus.id.model.value.AUID
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.Atom.Companion.atomic
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker

interface UserBalanceAbstractRepository {
    suspend fun getUserBalance(auid: AUID, ticker: Ticker): Amount = atomic { getUserBalance(this, auid, ticker) }
    suspend fun getUserBalance(atom: Atom, auid: AUID, ticker: Ticker): Amount
}