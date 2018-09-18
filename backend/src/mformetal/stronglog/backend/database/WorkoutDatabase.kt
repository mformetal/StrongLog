package mformetal.stronglog.backend.database

import io.ktor.application.Application
import mformetal.stronglog.models.Workout
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.expressions.count
import org.jetbrains.squash.query.from
import org.jetbrains.squash.query.select
import org.jetbrains.squash.results.get
import org.jetbrains.squash.schema.create
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values
import java.io.File

class WorkoutDatabase(application: Application) {

    val db: DatabaseConnection

    init {
        val file = File("/Users/mbpeele/Databases/stronglog.db")
        db = H2Connection.create(file.canonicalFile.absolutePath).apply {
            transaction {
                databaseSchema().create(WorkoutTable)
            }
        }
    }
}