package mformetal.moddly.backend

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentConverter
import io.ktor.features.ContentNegotiation
import io.ktor.features.suitableCharset
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.request.ApplicationReceiveRequest
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

@Serializable
class Example(val title: String)

val model = Example("Miles is Great")

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
//        install(ContentNegotiation) {
//            register(ContentType.Application.Json, object : ContentConverter {
//                override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
//                    return null
//                }
//
//                override suspend fun convertForSend(context: PipelineContext<Any, ApplicationCall>, contentType: ContentType, value: Any): Any? {
//                    val json = JSON.stringify(value)
//                    return TextContent(json, contentType.withCharset(context.call.suitableCharset()))
//                }
//            })
//        }
//        routing {
//            get("/") {
//                call.respond(model)
//            }
//        }
        routing {
            get("/") {
                call.respondText { "Hello World" }
            }
        }
    }.start(wait = true)
}