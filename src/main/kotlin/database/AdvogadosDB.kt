package database

import org.jetbrains.exposed.sql.*
import java.util.*

object AdvogadosDB : Table() {
    val id : Column<UUID> = uuid("id").primaryKey().uniqueIndex()
    val nome = varchar("nome", length = 255)
    val oab = integer("oab")
    val createdAt = varchar("createdAt", length = 255)
}

object AdvogadosProcessosDB: Table() {
    val processoId: Column<UUID> = uuid("processoId").references(ProcessosDB.id, onDelete = ReferenceOption.CASCADE)
    val advogadosId: Column<UUID> = uuid("advogadosId").references(AdvogadosDB.id, onDelete = ReferenceOption.CASCADE)
}