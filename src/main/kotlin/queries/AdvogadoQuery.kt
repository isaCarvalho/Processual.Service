package queries

import database.AdvogadosDB
import models.read.advogado.AdvogadoReadModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import valueobjects.OAB
import java.util.*

interface IAdvogadoQuery {
    suspend fun get(id: UUID): AdvogadoReadModel?

    suspend fun getBatch(ids: List<UUID>) : List<AdvogadoReadModel>

    suspend fun getAll() : List<AdvogadoReadModel>
}

class AdvogadoQuery : IAdvogadoQuery {

    override suspend fun get(id: UUID) : AdvogadoReadModel? {
        val row = transaction {
            AdvogadosDB.select {
                addLogger(StdOutSqlLogger)
                AdvogadosDB.id eq id
            }.firstOrNull()
        }

        return row?.asAdvogadoReadModel()
    }

    override suspend fun getBatch(ids: List<UUID>): List<AdvogadoReadModel> {

        val rows = transaction {
            AdvogadosDB.select {
                addLogger(StdOutSqlLogger)
                AdvogadosDB.id inList ids
            }
        }

        return rows.map { it.asAdvogadoReadModel() }
    }

    override suspend fun getAll(): List<AdvogadoReadModel> {

        val rows = transaction {
            AdvogadosDB.selectAll()
        }

        return rows.map { it.asAdvogadoReadModel() }
    }
}


private fun ResultRow.asAdvogadoReadModel() = AdvogadoReadModel(
    this[AdvogadosDB.id],
    this[AdvogadosDB.nome],
    OAB(this[AdvogadosDB.oab])
)