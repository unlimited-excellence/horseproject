package com.axus.winelore

import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.*
import com.axus.winelore.model.entity.WineSample.Code
import com.axus.winelore.model.entity.WineSampleAssessment.Mark
import eth.likespro.commons.models.Pagination
import eth.likespro.commons.models.value.Iteration
import eth.likespro.commons.models.value.Timestamp
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Contextual
import org.json.JSONObject

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
    fun filterCompetitions(
        organizer: AUID?,
        pagination: Pagination
    ): List<Competition>

    // <============ Commission Service ============>

    fun createCommission(competitionId: Competition.Id, name: Commission.Name, tokenId: Token.Id): Commission
    fun getCommission(id: Commission.Id): Commission?
    fun getCommissionByCompetitionIdAndName(competitionId: Competition.Id, name: Commission.Name): Commission?
    fun startCommission(auid: AUID, commissionId: Commission.Id, tokenId: Token.Id)

    // <============ CommissionParticipant Service ============>

    fun createCommissionParticipant(commissionId: Commission.Id, auid: AUID, role: CommissionParticipant.Role, tokenId: Token.Id): CommissionParticipant
    fun isExistingCommissionParticipantByCommissionIdAndAUID(commissionId: Commission.Id, auid: AUID): Boolean
    fun getCommissionParticipantByCommissionIdAndAUID(commissionId: Commission.Id, auid: AUID): CommissionParticipant?
    fun getCommissionsByParticipant(auid: AUID, pagination: Pagination): List<Pair<Commission, CommissionParticipant>>

    // <============ WineSample Service ============>
    
    fun createWineSample(commissionId: Commission.Id, wineId: Wine.Id, code: WineSample.Code, previousWineSampleId: WineSample.Id, tokenId: Token.Id): WineSample
    fun isExistingWineSample(id: WineSample.Id): Boolean
    fun getWineSample(id: WineSample.Id): WineSample?
    fun getWineSampleByCommissionIdAndCode(commissionId: Commission.Id, code: WineSample.Code): WineSample?
    fun filterWineSamples(
        commissionId: Commission.Id?,
        wineId: Wine.Id?,
        code: Code?,
        previousWineSampleId: WineSample.Id?,
        pagination: Pagination
    ): List<WineSample>

    // <============ WineSampleAssessment Service ============>

    fun createWineSampleAssessment(
        commissionId: Commission.Id,
        from: AUID,
        wineSampleId: WineSample.Id,
        mark: Mark,
        metadata: JSONObject = JSONObject(),
        tokenId: Token.Id,
    ): WineSampleAssessment
}