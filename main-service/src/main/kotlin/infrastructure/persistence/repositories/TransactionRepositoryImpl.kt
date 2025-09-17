package infrastructure.persistence.repositories

import com.axus.id.model.value.AUID
import domain.ports.repositories.TransactionRepository
import eth.likespro.atomarix.Atom
import eth.likespro.atomarix.adapters.AtomarixExposedAdapter
import eth.likespro.commons.models.Option
import eth.likespro.commons.models.Pagination
import infrastructure.persistence.tables.TransactionsTable
import io.ktor.server.http.content.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.unlimitedexcellence.horseproject.model.entity.Transaction
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker

class TransactionRepositoryImpl : TransactionRepository {
    init {
        transaction {
            SchemaUtils.create(TransactionsTable)
        }
    }

    override suspend fun filter(
        atom: Atom,
        sender: Option<AUID?>,
        recipient: Option<AUID?>,
        ticker: Option<Ticker>
    ): List<Transaction> = AtomarixExposedAdapter.runWithAdapter(atom) {
        TransactionsTable.selectAll()
            .filterQuery(sender, recipient, ticker)
            .map { TransactionsTable.fromRow(it) }
    }

    override suspend fun filterAndAccumulateAmount(
        atom: Atom,
        sender: Option<AUID?>,
        recipient: Option<AUID?>,
        ticker: Option<Ticker>
    ): Amount = AtomarixExposedAdapter.runWithAdapter(atom) {
        TransactionsTable.select(TransactionsTable.amount.sum())
            .filterQuery(sender, recipient, ticker)
            .singleOrNull()
            .let { it?.get(TransactionsTable.amount.sum()) ?: 0L }
            .let { Amount(it) }
    }

    override suspend fun count(atom: Atom, pagination: Pagination?): Long {
        TODO("Not yet implemented")
    }

    override suspend fun create(atom: Atom, entity: Transaction): Transaction = AtomarixExposedAdapter.runWithAdapter(atom) {
        TransactionsTable.insert {
            TransactionsTable.copy(entity, it)
        }.let { entity }
    }

    override suspend fun delete(atom: Atom, id: Transaction.Id) {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(atom: Atom, pagination: Pagination): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(atom: Atom, id: Transaction.Id): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun isExisting(atom: Atom, id: Transaction.Id): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(atom: Atom, entity: Transaction): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(atom: Atom, entity: Transaction): Transaction {
        TODO("Not yet implemented")
    }

    fun Query.filterQuery(
        sender: Option<AUID?>,
        recipient: Option<AUID?>,
        ticker: Option<Ticker>
    ) = this
            .let { if(sender is Option.Some<AUID?>) it.andWhere { TransactionsTable.sender eq sender.value?.value } else it }
            .let { if(recipient is Option.Some<AUID?>) it.andWhere { TransactionsTable.recipient eq recipient.value?.value } else it }
            .let { if(ticker is Option.Some<Ticker>) it.andWhere { TransactionsTable.ticker eq ticker.value.value } else it }
}