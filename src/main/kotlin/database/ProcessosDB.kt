package database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object ProcessosDB : Table() {
    val id: Column<UUID> = uuid("id").primaryKey().uniqueIndex()
    val numero = integer("numero")
    val partes = varchar("partes", length = 500)
    val comarca = varchar("comarca", length = 255).nullable()
    val vara = varchar("vara", length = 255).nullable()
    val competenciaId: Column<UUID?> = uuid("competenciaId").nullable()
    val instancia = integer("instancia").nullable()
    val createdAt = varchar("createdAt", length = 255)
}