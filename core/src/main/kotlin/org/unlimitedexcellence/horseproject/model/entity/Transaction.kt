package org.unlimitedexcellence.horseproject.model.entity

import com.axus.id.model.value.AUID
import eth.likespro.commons.models.Entity
import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker
import java.util.*

@Serializable
data class Transaction(
    override val id: Id = Id(),
    val sender: AUID?,
    val recipient: AUID?,
    val ticker: Ticker,
    val amount: Amount,
    val createdAt: Timestamp = Timestamp.now(),
) : Entity<Transaction.Id>, Validatable<Transaction> {
    open class IsInvalidException(override val message: String) : RuntimeException()
    class SenderAndRecipientAreBothNullException : IsInvalidException("Transaction sender and recipient cannot be both null.")

    open class IsIncorrectException(override val message: String) : RuntimeException()
    class SenderAndRecipientAreTheSameException : IsIncorrectException("Transaction sender and recipient cannot be the same.")
    class AmountIsZeroException : IsIncorrectException("Transaction Amount cannot be zero.")
    class AmountIsNegativeException : IsIncorrectException("Transaction Amount cannot be negative.")
    class InsufficientBalanceException : IsIncorrectException("Insufficient balance for the transaction.")

    @Serializable
    data class Id(
        val value: String = UUID.randomUUID().toString()
    ): Value, Validatable<Id> {
        open class IsInvalidException(override val message: String) : RuntimeException()
        class LengthIsInvalidException(message: String) : IsInvalidException(message)

        init {
            throwIfInvalid()
        }

        override fun throwIfInvalid(): Id {
            if(value.length != 36)
                throw LengthIsInvalidException("Transaction ID must be 36 characters long")

            return this
        }
    }

    init {
        throwIfInvalid()
    }

    override fun throwIfInvalid(): Transaction = this.also {
        if(sender == null && recipient == null)
            throw SenderAndRecipientAreBothNullException()
    }
}