package mformetal.stronglog.backend

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mformetal.stronglog.backend.database.WorkoutDatabase
import java.io.File
import mformetal.stronglog.models.Muscles
import mformetal.stronglog.models.Workout
import javax.smartcardio.Card
import com.squareup.moshi.Types.newParameterizedType

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        val database = WorkoutDatabase(this)

        routing {
            get("/") {

            }
        }
    }.start(wait = true)
}