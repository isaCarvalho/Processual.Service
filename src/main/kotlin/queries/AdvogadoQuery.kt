package queries

import database.AdvogadosDB
import entities.Advogado
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import valueobjects.OAB
import java.util.*

interface IAdvogadoQuery {
    suspend fun get(id: UUID): Advogado?

    suspend fun getBatch(ids: List<UUID>) : List<Advogado>

    suspend fun getAll() : List<Advogado>
}

class AdvogadoQuery : IAdvogadoQuery {

    override suspend fun get(id: UUID) : Advogado? {
        val row = transaction {
            AdvogadosDB.select {
                addLogger(StdOutSqlLogger)
                AdvogadosDB.id eq id
            }.firstOrNull()
        }

        return row?.asAdvogado()
    }

    override suspend fun getBatch(ids: List<UUID>): List<Advogado> {

        val rows = transaction {
            AdvogadosDB.select {
                addLogger(StdOutSqlLogger)
                AdvogadosDB.id inList ids
            }
        }

        return rows.map { it.asAdvogado() }
    }

    override suspend fun getAll(): List<Advogado> {

        val rows = transaction {
            AdvogadosDB.selectAll()
        }

        return rows.map { it.asAdvogado() }
    }
}


private fun ResultRow.asAdvogado() = Advogado(
    this[AdvogadosDB.id],
    this[AdvogadosDB.nome],
    OAB(this[AdvogadosDB.oab])
)