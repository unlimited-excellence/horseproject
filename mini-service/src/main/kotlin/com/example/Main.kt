package com.example

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.Serializable
import io.ktor.serialization.kotlinx.json.*

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { json() }
    routing {
        get("/") {
            call.respond(
                mapOf(
                    "status" to "ok",
                    "service" to "mini-service",
                    "message" to "Hello from Mini Service! 🚀"
                )
            )
        }
        get("/health") { call.respond(mapOf("ok" to true)) }
    }
}
