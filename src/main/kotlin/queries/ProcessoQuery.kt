package queries

import aggregates.Processo
import database.ProcessosDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

interface IProcessoQuery {
    suspend fun get(id: UUID): Processo?

    suspend fun getBatch(ids: List<UUID>) : List<Processo>

    suspend fun getAll() : List<Processo>
}

class ProcessoQuery : IProcessoQuery {

    override suspend fun get(id: UUID) : Processo? {
        val row = transaction {
            ProcessosDB.select {
                addLogger(StdOutSqlLogger)
                ProcessosDB.id eq id
            }.firstOrNull()
        }

        return row?.asProcesso()
    }

    override suspend fun getBatch(ids: List<UUID>): List<Processo> {

        val rows = transaction {
            ProcessosDB.select {
                addLogger(StdOutSqlLogger)
                ProcessosDB.id inList ids
            }
        }

        return rows.map { it.asProcesso() }
    }

    override suspend fun getAll(): List<Processo> {

        val rows = transaction {
            ProcessosDB.selectAll()
        }

        return rows.map { it.asProcesso() }
    }
}

private fun ResultRow.asProcesso() = Processo(
    this[ProcessosDB.id],
    this[ProcessosDB.numero],
    null,
    this[ProcessosDB.partes].split(";").toList(),
    null,
    null,
    null
)