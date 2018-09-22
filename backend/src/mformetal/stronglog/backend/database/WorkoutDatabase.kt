package mformetal.stronglog.backend.database

import io.ktor.application.Application
import mformetal.stronglog.models.Muscles
import mformetal.stronglog.models.Workout
import mformetal.stronglog.scraper.Scraper
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.expressions.count
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.from
import org.jetbrains.squash.query.select
import org.jetbrains.squash.query.where
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.results.get
import org.jetbrains.squash.schema.create
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values
import java.io.File

class WorkoutDatabase(application: Application) {

    val db: DatabaseConnection = H2Connection.createMemoryConnection()

    init {
        db.transaction {
            databaseSchema().create(WorkoutTable)

            val workouts = Scraper().scrape(false)
            for (workout in workouts) {
                insertInto(WorkoutTable).values {
                    it[title] = workout.title
                }.execute()
            }
        }
    }

    fun byTitle(title: String) = db.transaction {
        from(WorkoutTable).where { WorkoutTable.title eq title }.execute().single().toWorkout()
    }

    private fun ResultRow.toWorkout() =
            Workout(
                    title = get(WorkoutTable.title),
                    classification = null,
                    muscles = Muscles("", listOf()),
                    gifUrl = "",
                    videoUrl = ""
            )
}