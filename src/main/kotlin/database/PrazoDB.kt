package database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import java.util.*

object PrazoDB : Table() {
    val id: Column<UUID> = uuid("id").primaryKey().uniqueIndex()
    val quantidadeEmDias = integer("quantidadeEmDias")
    val dueDate = varchar("dueDate", length = 255)
    val processoId = uuid("processoId").references(ProcessosDB.id, onDelete = ReferenceOption.CASCADE)
    val createdAt = varchar("createdAt", length = 255)
}