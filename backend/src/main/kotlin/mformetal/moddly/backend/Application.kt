package mformetal.moddly.backend

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentConverter
import io.ktor.features.ContentNegotiation
import io.ktor.features.suitableCharset
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.request.ApplicationReceiveRequest
import io.ktor.request.contentCharset
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.experimental.io.ByteReadChannel
import kotlinx.coroutines.experimental.io.jvm.javaio.toInputStream

class Example(val title: String)

val model = Example("Miles is Great")

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            gson {  }
        }
        routing {
            get("/") {
                call.respond(model)
            }
        }
    }.start(wait = true)
}