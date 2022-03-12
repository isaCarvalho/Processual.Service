package database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object PrazoDB : Table() {
    val id: Column<UUID> = uuid("id").primaryKey().uniqueIndex()
    val quantidadeEmDias = integer("quantidadeEmDias")
    val processoId = uuid("processoId")
    val createdAt = date("createdAt")
}