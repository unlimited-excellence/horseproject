package presentation.rest

import com.axus.winelore.WineLoreEndpoint
import eth.likespro.commons.models.WrappedException
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import presentation.rest.commission.commissionRouting
import presentation.rest.commissionparticipant.commissionParticipantRouting
import presentation.rest.competition.competitionRouting
import presentation.rest.wine.wineRouting

val lpfcpGateway: WineLoreEndpoint by inject(WineLoreEndpoint::class.java)
val exceptionDetailsConfiguration = WrappedException.DetailsConfiguration.INCLUDE_MESSAGE_AND_CAUSE

fun Application.configureREST() {
    install(ContentNegotiation) {
        gson {  }
    }
    routing {
        wineRouting()
        competitionRouting()
        commissionRouting()
        commissionParticipantRouting()
    }
}