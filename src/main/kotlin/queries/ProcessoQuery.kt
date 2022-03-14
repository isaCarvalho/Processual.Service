package queries

import database.ProcessosDB
import models.read.processo.ProcessoReadModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

interface IProcessoQuery {
    suspend fun get(id: UUID): ProcessoReadModel?

    suspend fun getBatch(ids: List<UUID>) : List<ProcessoReadModel>

    suspend fun getAll() : List<ProcessoReadModel>
}

class ProcessoQuery : IProcessoQuery {

    override suspend fun get(id: UUID) : ProcessoReadModel? {
        val row = transaction {
            ProcessosDB.select {
                addLogger(StdOutSqlLogger)
                ProcessosDB.id eq id
            }.firstOrNull()
        }

        return row?.asProcessoReadModel()
    }

    override suspend fun getBatch(ids: List<UUID>): List<ProcessoReadModel> {

        val rows = transaction {
            ProcessosDB.select {
                addLogger(StdOutSqlLogger)
                ProcessosDB.id inList ids
            }
        }

        return rows.map { it.asProcessoReadModel() }
    }

    override suspend fun getAll(): List<ProcessoReadModel> {

        val rows = transaction {
            ProcessosDB.selectAll()
        }

        return rows.map { it.asProcessoReadModel() }
    }
}

private fun ResultRow.asProcessoReadModel() = ProcessoReadModel(
    this[ProcessosDB.id],
    this[ProcessosDB.numero],
    this[ProcessosDB.partes].split(";").toList()
)