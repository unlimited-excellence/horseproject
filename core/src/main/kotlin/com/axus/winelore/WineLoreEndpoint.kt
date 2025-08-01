package com.axus.winelore

import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.*
import eth.likespro.commons.models.value.Iteration
import eth.likespro.commons.models.value.Timestamp

interface WineLoreEndpoint {
    // <============ Wine Service ============>

    fun createWine(
        producer: AUID,
        name: Wine.Name,
        iteration: Iteration,
        color: Wine.Color,
        type: Wine.Type,
        tokenId: Token.Id
    ): Wine
    fun isExistingWine(id: Wine.Id): Boolean
    fun getWine(id: Wine.Id): Wine?
    fun getWineByProducerAndNameAndIteration(producer: AUID, name: Wine.Name, iteration: Iteration): Wine?

    // <============ Competition Service ============>

    fun createCompetition(organizer: AUID, name: Competition.Name, plannedStartAt: Timestamp, tokenId: Token.Id): Competition
    fun getCompetition(id: Competition.Id): Competition?
    fun getCompetitionByName(name: Competition.Name): Competition?

    // <============ Commission Service ============>

    fun createCommission(competitionId: Competition.Id, name: Commission.Name, tokenId: Token.Id): Commission
    fun getCommission(id: Commission.Id): Commission?
    fun getCommissionByCompetitionIdAndName(competitionId: Competition.Id, name: Commission.Name): Commission?

    // <============ CommissionParticipant Service ============>

    fun createCommissionParticipant(commissionId: Commission.Id, auid: AUID, role: CommissionParticipant.Role, tokenId: Token.Id): CommissionParticipant
    fun getCommissionParticipantByCommissionIdAndAUID(commissionId: Commission.Id, auid: AUID): CommissionParticipant?

    // <============ WineSample Service ============>
    
    fun createWineSample(commissionId: Commission.Id, wineId: Wine.Id, code: WineSample.Code, previousWineSampleId: WineSample.Id, tokenId: Token.Id): WineSample
    fun isExistingWineSample(id: WineSample.Id): Boolean
    fun getWineSample(id: WineSample.Id): WineSample?
    fun getWineSampleByCommissionIdAndCode(commissionId: Commission.Id, code: WineSample.Code): WineSample?
}