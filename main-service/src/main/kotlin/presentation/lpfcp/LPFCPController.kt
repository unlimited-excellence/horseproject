package presentation.lpfcp

import application.usecase.CreateTransactionRequest
import application.usecase.FetchUserBalanceRequest
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import eth.likespro.commons.models.WrappedException
import eth.likespro.lpfcp.LPFCP
import eth.likespro.lpfcp.ktor.Ktor.lpfcp
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import org.unlimitedexcellence.horseproject.HorseProjectEndpoint
import org.unlimitedexcellence.horseproject.model.entity.Transaction
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker

class MainServiceImpl : HorseProjectEndpoint {
    // <============ Transaction Service ============>

    @LPFCP.ExposedFunction
    override fun fetchUserBalance(auid: AUID, ticker: Ticker): Amount = runBlocking {
        FetchUserBalanceRequest(auid, ticker).execute()
    }

    @LPFCP.ExposedFunction
    override fun createTransaction(sender: AUID?, recipient: AUID?, ticker: Ticker, amount: Amount, tokenId: Token.Id): Transaction = runBlocking {
        CreateTransactionRequest(sender, recipient, ticker, amount, tokenId).execute()
    }
}

fun Application.configureLPFCP() {
    routing {
        lpfcp(MainServiceImpl(), exceptionDetailsConfiguration = WrappedException.DetailsConfiguration.INCLUDE_MESSAGE_AND_CAUSE)
    }
}