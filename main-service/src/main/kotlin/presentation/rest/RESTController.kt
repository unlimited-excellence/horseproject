package presentation.rest

import eth.likespro.commons.models.WrappedException
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import org.unlimitedexcellence.horseproject.HorseProjectEndpoint

val lpfcpGateway: HorseProjectEndpoint by inject(HorseProjectEndpoint::class.java)
val exceptionDetailsConfiguration = WrappedException.DetailsConfiguration.INCLUDE_MESSAGE_AND_CAUSE

fun Application.configureREST() {
    install(ContentNegotiation) {
        gson {  }
    }
    routing {

    }
}