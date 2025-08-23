package presentation.rest.commission

import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import eth.likespro.commons.models.EncodableResult
import eth.likespro.commons.network.RESTAPIUtils.toResponse
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.rest.dto.CommissionDTO
import presentation.rest.exceptionDetailsConfiguration
import presentation.rest.lpfcpGateway

fun Routing.commissionRouting() {
    post("/createCommission") {
        val request = call.receive<CreateCommissionRequest>()
        call.respond(
            EncodableResult.runCatching {
                lpfcpGateway.createCommission(
                    Competition.Id(request.competitionId),
                    Commission.Name(request.name),
                    Token.Id(request.tokenId),
                )
            }
                .applyExceptionDetailsConfiguration(exceptionDetailsConfiguration)
                .toResponse { CommissionDTO(it) }
        )
    }

    post("/startCommission") {
        val request = call.receive<StartCommissionRequest>()
        call.respond(
            EncodableResult.runCatching {
                lpfcpGateway.startCommission(
                    AUID(request.auid),
                    Commission.Id(request.commissionId),
                    Token.Id(request.tokenId),
                )
            }
                .applyExceptionDetailsConfiguration(exceptionDetailsConfiguration)
                .toResponse {  }
        )
    }
}