package presentation.lpfcp

import application.usecase.*
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLoreEndpoint
import com.axus.winelore.model.entity.*
import eth.likespro.commons.models.WrappedException
import eth.likespro.commons.models.value.Iteration
import eth.likespro.commons.models.value.Timestamp
import eth.likespro.lpfcp.LPFCP
import eth.likespro.lpfcp.ktor.Ktor.lpfcp
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

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
    override fun createCommissionParticipant(
        commissionId: Commission.Id,
        auid: AUID,
        role: CommissionParticipant.Role,
        tokenId: Token.Id
    ): CommissionParticipant = runBlocking {
        CreateCommissionParticipantRequest(commissionId, auid, role, tokenId).execute()
    }

    @LPFCP.ExposedFunction
    override fun getCommissionParticipantByCommissionIdAndAUID(
        commissionId: Commission.Id,
        auid: AUID
    ): CommissionParticipant? = runBlocking {
        GetCommissionParticipantByCompetitionIdAndAUIDRequest(commissionId, auid).execute()
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
}

fun Application.configureLPFCP() {
    routing {
        lpfcp(MainServiceImpl(), exceptionDetailsConfiguration = WrappedException.DetailsConfiguration.INCLUDE_MESSAGE_AND_CAUSE)
    }
}