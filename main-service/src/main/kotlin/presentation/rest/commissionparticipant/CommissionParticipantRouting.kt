package presentation.rest.commissionparticipant

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import eth.likespro.commons.models.EncodableResult
import eth.likespro.commons.network.RESTAPIUtils.toResponse
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.rest.dto.CompetitionParticipantDTO
import presentation.rest.dto.PairOfCommissionAndCommissionParticipantDTO
import presentation.rest.exceptionDetailsConfiguration
import presentation.rest.lpfcpGateway

fun Routing.commissionParticipantRouting() {
    post("/getCommissionParticipantByCommissionIdAndAUID") {
        val request = call.receive<GetCommissionParticipantByCommissionIdAndAUIDRequest>()
        call.respond(
            EncodableResult.runCatching {
                lpfcpGateway.getCommissionParticipantByCommissionIdAndAUID(
                    Commission.Id(request.commissionId),
                    AUID(request.auid),
                )
            }
                .applyExceptionDetailsConfiguration(exceptionDetailsConfiguration)
                .toResponse { it?.let { CompetitionParticipantDTO(it) } }
        )
    }
    post("/getCommissionsByParticipant") {
        val request = call.receive<GetCommissionsByParticipantRequest>()
        call.respond(
            EncodableResult.runCatching {
                lpfcpGateway.getCommissionsByParticipant(
                    AUID(request.auid),
                    request.pagination,
                )
            }
                .applyExceptionDetailsConfiguration(exceptionDetailsConfiguration)
                .toResponse { it.map { PairOfCommissionAndCommissionParticipantDTO(it) } }
        )
    }
}