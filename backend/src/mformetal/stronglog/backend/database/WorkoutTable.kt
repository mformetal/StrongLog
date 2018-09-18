package mformetal.stronglog.backend.database

import org.jetbrains.squash.definition.*

object WorkoutTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = text("title")
}