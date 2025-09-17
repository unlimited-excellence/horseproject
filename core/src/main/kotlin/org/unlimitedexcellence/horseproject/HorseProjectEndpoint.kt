package org.unlimitedexcellence.horseproject

import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import org.unlimitedexcellence.horseproject.model.entity.Transaction
import org.unlimitedexcellence.horseproject.model.value.Amount
import org.unlimitedexcellence.horseproject.model.value.Ticker

interface HorseProjectEndpoint {
    // <============ Transaction Service ============>

    fun fetchUserBalance(auid: AUID, ticker: Ticker): Amount
    fun createTransaction(sender: AUID?, recipient: AUID?, ticker: Ticker, amount: Amount, tokenId: Token.Id): Transaction
}