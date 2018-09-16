package mformetal.stronglog.backend.database

import org.jetbrains.squash.definition.TableDefinition
import org.jetbrains.squash.definition.autoIncrement
import org.jetbrains.squash.definition.integer
import org.jetbrains.squash.definition.primaryKey

object WorkoutTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
}