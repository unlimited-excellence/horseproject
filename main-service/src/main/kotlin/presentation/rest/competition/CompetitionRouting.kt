package presentation.rest.competition

import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Competition
import eth.likespro.commons.models.EncodableResult
import eth.likespro.commons.models.value.Timestamp
import eth.likespro.commons.network.RESTAPIUtils.toResponse
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.rest.dto.CompetitionDTO
import presentation.rest.exceptionDetailsConfiguration
import presentation.rest.lpfcpGateway

fun Routing.competitionRouting() {
    post("/createCompetition") {
        val request = call.receive<CreateCompetitionRequest>()
        call.respond(
            EncodableResult.runCatching {
                lpfcpGateway.createCompetition(
                    AUID(request.organizer),
                    Competition.Name(request.name),
                    Timestamp(request.plannedStartAt),
                    Token.Id(request.tokenId),
                )
            }
                .applyExceptionDetailsConfiguration(exceptionDetailsConfiguration)
                .toResponse { CompetitionDTO(it) }
        )
    }

    post("/filterCompetitions") {
        val request = call.receive<FilterCompetitionsRequest>()
        call.respond(
            EncodableResult.runCatching {
                lpfcpGateway.filterCompetitions(
                    request.organizer?.let { AUID(it) },
                    request.pagination,
                )
            }
                .applyExceptionDetailsConfiguration(exceptionDetailsConfiguration)
                .toResponse { it.map { competition -> CompetitionDTO(competition) } }
        )
    }
}