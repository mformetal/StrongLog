package mformetal.stronglog.backend

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

class Example(val title: String)

val model = Example("Miles is Great")

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            gson {

            }
        }
        routing {
            get("/") {
                call.respond(model)
            }
        }
    }.start(wait = true)
}