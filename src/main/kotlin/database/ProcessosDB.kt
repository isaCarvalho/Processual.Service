package database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object ProcessosDB : Table() {
    val id: Column<UUID> = uuid("id").primaryKey().uniqueIndex()
    val numero = integer("numero")
    val comarca = varchar("comarca", length = 255)
    val vara = varchar("vara", length = 255)
    val competenciaId: Column<UUID> = uuid("competenciaId")
    val instancia = integer("instancia")
    val createdAt = date("createdAt")
}