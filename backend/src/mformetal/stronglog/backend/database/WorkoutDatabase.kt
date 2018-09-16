package mformetal.stronglog.backend.database

import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.expressions.count
import org.jetbrains.squash.query.from
import org.jetbrains.squash.query.select
import org.jetbrains.squash.results.get
import org.jetbrains.squash.schema.create

class WorkoutDatabase(val db: DatabaseConnection = H2Connection.createMemoryConnection()) {

    init {
        db.transaction {
            databaseSchema().create(WorkoutTable)
        }
    }
}