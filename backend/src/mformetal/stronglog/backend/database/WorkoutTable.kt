package mformetal.stronglog.backend.database

import org.jetbrains.squash.definition.*

object WorkoutTable : TableDefinition("Workout") {
    val id = integer("id").autoIncrement().primaryKey()
    val title = text("title")
}