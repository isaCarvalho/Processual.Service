package database

import org.jetbrains.exposed.sql.*
import java.util.*

object AdvogadosDB : Table() {
    val nome = varchar("nome", length = 255).primaryKey()
    val oab = integer("oab").primaryKey()
    val processoId: Column<UUID> = uuid("processoId").primaryKey()
}