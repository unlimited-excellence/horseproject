package infrastructure.persistence.tables

import com.axus.id.model.value.AUID
import eth.likespro.commons.models.value.Timestamp
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.unlimitedexcellence.horseproject.model.entity.Transaction
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker

object TransactionsTable : Table("transactions") {
    val id = varchar("id", 36).uniqueIndex()
    val sender = long("sender").nullable()
    val recipient = long("recipient").nullable()
    val ticker = text("ticker")
    val amount = long("amount")
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)

    fun fromRow(row: ResultRow) = Transaction(
        id = Transaction.Id(row[id]),
        sender = row[sender]?.let { AUID(it) },
        recipient = row[recipient]?.let { AUID(it) },
        ticker = Ticker(row[ticker]),
        amount = Amount(row[amount]),
        createdAt = Timestamp(row[createdAt])
    )

    fun copy(transaction: Transaction, to: UpdateBuilder<Int>) {
        to[id] = transaction.id.value
        to[sender] = transaction.sender?.value
        to[recipient] = transaction.recipient?.value
        to[ticker] = transaction.ticker.value
        to[amount] = transaction.amount.value
        to[createdAt] = transaction.createdAt.toInstant()
    }
}