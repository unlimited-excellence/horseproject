package app

import app.di.ModuleLoader.configureDI
import infrastructure.persistence.DatabaseFactory
import presentation.lpfcp.configureLPFCP
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.*
import org.koin.java.KoinJavaComponent.get

val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDI()
    get<DatabaseFactory>(DatabaseFactory::class.java).init()
    configureLPFCP()
}
