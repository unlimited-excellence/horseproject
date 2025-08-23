package presentation.rest.wine

import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Wine
import eth.likespro.commons.models.EncodableResult
import eth.likespro.commons.models.value.Iteration
import eth.likespro.commons.network.RESTAPIUtils.toResponse
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.rest.competition.FilterCompetitionsRequest
import presentation.rest.dto.CompetitionDTO
import presentation.rest.exceptionDetailsConfiguration
import presentation.rest.lpfcpGateway

fun Routing.wineRouting() {
    post("/createWine") {
        val request = call.receive<CreateWineRequest>()
        call.respond(
            EncodableResult.runCatching {
                lpfcpGateway.createWine(
                    AUID(request.producer),
                    Wine.Name(request.name),
                    Iteration(request.iteration),
                    request.color,
                    request.type,
                    Token.Id(request.tokenId)
                )
            }
                .applyExceptionDetailsConfiguration(exceptionDetailsConfiguration)
                .toResponse {  }
        )
    }
}