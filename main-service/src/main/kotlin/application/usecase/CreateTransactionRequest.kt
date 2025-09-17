package application.usecase

import app.logger
import com.axus.id.FullPermission
import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import domain.ports.repositories.TransactionRepository
import domain.ports.repositories.UserBalanceAbstractRepository
import eth.likespro.atomarix.Atom.Companion.atomic
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import org.unlimitedexcellence.horseproject.HorseProjectPermissionBuilder
import org.unlimitedexcellence.horseproject.model.InsufficientPermissionsException
import org.unlimitedexcellence.horseproject.model.entity.Transaction
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker

@Serializable
data class CreateTransactionRequest(
    val sender: AUID?,
    val recipient: AUID?,
    val ticker: Ticker,
    val amount: Amount,
    val tokenId: Token.Id,
) {
    suspend fun execute(): Transaction {
        val session: Session = get<Session>(Session::class.java).refreshedIfExpired()
        val transactionRepository: TransactionRepository by inject(TransactionRepository::class.java)
        val userBalanceAbstractRepository: UserBalanceAbstractRepository by inject(UserBalanceAbstractRepository::class.java)

        logger.warn { FullPermission(HorseProjectPermissionBuilder.createTransaction(AUID(session.auid), sender, recipient)) }
        if(!session.hasTokenPermission(tokenId.value, FullPermission(HorseProjectPermissionBuilder.createTransaction(AUID(session.auid), sender, recipient))))
            throw InsufficientPermissionsException()

        if(sender == recipient)
            throw Transaction.SenderAndRecipientAreTheSameException()
        if(amount < Amount.ZERO)
            throw Transaction.AmountIsNegativeException()
        if(amount == Amount.ZERO)
            throw Transaction.AmountIsZeroException()

        val transaction = Transaction(
            sender = sender,
            recipient = recipient,
            ticker = ticker,
            amount = amount,
        )

        return atomic {
            if(sender != null && userBalanceAbstractRepository.getUserBalance(this, sender, ticker) < amount)
                throw Transaction.InsufficientBalanceException()
            transactionRepository.create(this, transaction)
        }
    }
}