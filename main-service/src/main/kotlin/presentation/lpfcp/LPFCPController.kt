package presentation.lpfcp

import application.usecase.*
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLoreEndpoint
import com.axus.winelore.model.entity.*
import eth.likespro.commons.models.Pagination
import eth.likespro.commons.models.WrappedException
import eth.likespro.commons.models.value.Iteration
import eth.likespro.commons.models.value.Timestamp
import eth.likespro.lpfcp.LPFCP
import eth.likespro.lpfcp.ktor.Ktor.lpfcp
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class MainServiceImpl : WineLoreEndpoint {
    // <============ Wine Service ============>

    @LPFCP.ExposedFunction
    override fun createWine(
        producer: AUID,
        name: Wine.Name,
        iteration: Iteration,
        color: Wine.Color,
        type: Wine.Type,
        tokenId: Token.Id
    ): Wine = runBlocking {
        CreateWineRequest(producer, name, iteration, color, type, tokenId).execute()
    }

    @LPFCP.ExposedFunction
    override fun isExistingWine(id: Wine.Id): Boolean = runBlocking {
        IsExistingWineRequest(id).execute()
    }

    @LPFCP.ExposedFunction
    override fun getWine(id: Wine.Id): Wine? = runBlocking {
        GetWineRequest(id).execute()
    }

    @LPFCP.ExposedFunction
    override fun getWineByProducerAndNameAndIteration(producer: AUID, name: Wine.Name, iteration: Iteration): Wine? = runBlocking {
        GetWineByProducerAndNameAndIterationRequest(producer, name, iteration).execute()
    }



    // <============ Competition Service ============>

    @LPFCP.ExposedFunction
    override fun createCompetition(
        organizer: AUID,
        name: Competition.Name,
        plannedStartAt: Timestamp,
        tokenId: Token.Id
    ): Competition = runBlocking {
        CreateCompetitionRequest(organizer, name, plannedStartAt, tokenId).execute()
    }

    @LPFCP.ExposedFunction
    override fun getCompetition(id: Competition.Id): Competition? = runBlocking {
        GetCompetitionRequest(id).execute()
    }

    @LPFCP.ExposedFunction
    override fun getCompetitionByName(name: Competition.Name): Competition? = runBlocking {
        GetCompetitionByNameRequest(name).execute()
    }

    @LPFCP.ExposedFunction
    override fun filterCompetitions(organizer: AUID?, pagination: Pagination): List<Competition> = runBlocking {
        FilterCompetitionsRequest(organizer, pagination).execute()
    }

    @LPFCP.ExposedFunction
    override fun createCommissionParticipant(
        commissionId: Commission.Id,
        auid: AUID,
        role: CommissionParticipant.Role,
        tokenId: Token.Id
    ): CommissionParticipant = runBlocking {
        CreateCommissionParticipantRequest(commissionId, auid, role, tokenId).execute()
    }

    @LPFCP.ExposedFunction
    override fun isExistingCommissionParticipantByCommissionIdAndAUID(
        commissionId: Commission.Id,
        auid: AUID
    ): Boolean = runBlocking {
        IsExistingCommissionParticipantByCommissionIdAndAUIDRequest(commissionId, auid).execute()
    }

    @LPFCP.ExposedFunction
    override fun getCommissionParticipantByCommissionIdAndAUID(
        commissionId: Commission.Id,
        auid: AUID
    ): CommissionParticipant? = runBlocking {
        GetCommissionParticipantByCommissionIdAndAUIDRequest(commissionId, auid).execute()
    }

    @LPFCP.ExposedFunction
    override fun getCommissionsByParticipant(
        auid: AUID,
        pagination: Pagination
    ): List<Pair<Commission, CommissionParticipant>> = runBlocking {
        GetCommissionsByParticipantRequest(auid, pagination).execute()
    }


    // <============ Commission Service ============>

    @LPFCP.ExposedFunction
    override fun createCommission(competitionId: Competition.Id, name: Commission.Name, tokenId: Token.Id) = runBlocking {
        CreateCommissionRequest(competitionId, name, tokenId).execute()
    }

    @LPFCP.ExposedFunction
    override fun getCommission(id: Commission.Id): Commission? = runBlocking {
        GetCommissionRequest(id).execute()
    }

    @LPFCP.ExposedFunction
    override fun getCommissionByCompetitionIdAndName(
        competitionId: Competition.Id,
        name: Commission.Name
    ): Commission? = runBlocking {
        GetCommissionByCompetitionIdAndNameRequest(competitionId, name).execute()
    }

    @LPFCP.ExposedFunction
    override fun startCommission(auid: AUID, commissionId: Commission.Id, tokenId: Token.Id) = runBlocking {
        StartCommissionRequest(auid, commissionId, tokenId).execute()
    }



    // <============ WineSample Service ============>

    @LPFCP.ExposedFunction
    override fun createWineSample(
        commissionId: Commission.Id,
        wineId: Wine.Id,
        code: WineSample.Code,
        previousWineSampleId: WineSample.Id,
        tokenId: Token.Id
    ): WineSample = runBlocking {
        CreateWineSampleRequest(commissionId, wineId, code, previousWineSampleId, tokenId).execute()
    }

    @LPFCP.ExposedFunction
    override fun isExistingWineSample(id: WineSample.Id): Boolean = runBlocking {
        IsExistingWineSampleRequest(id).execute()
    }

    @LPFCP.ExposedFunction
    override fun getWineSample(id: WineSample.Id): WineSample? = runBlocking {
        GetWineSampleRequest(id).execute()
    }

    @LPFCP.ExposedFunction
    override fun getWineSampleByCommissionIdAndCode(commissionId: Commission.Id, code: WineSample.Code): WineSample? = runBlocking {
        GetWineSampleByCommissionIdAndCodeRequest(commissionId, code).execute()
    }

    @LPFCP.ExposedFunction
    override fun filterWineSamples(
        commissionId: Commission.Id?,
        wineId: Wine.Id?,
        code: WineSample.Code?,
        previousWineSampleId: WineSample.Id?,
        pagination: Pagination
    ): List<WineSample> = runBlocking {
        FilterWineSamplesRequest(commissionId, wineId, code, previousWineSampleId, pagination).execute()
    }


    // <============ WineSampleAssessment Service ============>

    @LPFCP.ExposedFunction
    override fun createWineSampleAssessment(
        commissionId: Commission.Id,
        from: AUID,
        wineSampleId: WineSample.Id,
        mark: WineSampleAssessment.Mark,
        metadata: JSONObject,
        tokenId: Token.Id
    ): WineSampleAssessment = runBlocking {
        CreateWineSampleAssessmentRequest(commissionId, from, wineSampleId, mark, metadata, tokenId).execute()
    }
}

fun Application.configureLPFCP() {
    routing {
        lpfcp(MainServiceImpl(), exceptionDetailsConfiguration = WrappedException.DetailsConfiguration.INCLUDE_MESSAGE_AND_CAUSE)
    }
}