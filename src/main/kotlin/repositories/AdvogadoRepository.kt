package repositories

import database.AdvogadosDB
import entities.Advogado
import models.write.advogado.AdvogadoWriteModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import valueobjects.OAB
import java.util.UUID

interface IAdvogadoRepository : IRepository {
    suspend fun create(model: AdvogadoWriteModel)

    suspend fun delete(id: UUID)

    suspend fun update(model: AdvogadoWriteModel)

    suspend fun get(id: UUID): Advogado?

    suspend fun getBatch(ids: List<UUID>) : List<Advogado>
}

class AdvogadoRepository : IAdvogadoRepository {

    override suspend fun create(model: AdvogadoWriteModel) {

        transaction {
            AdvogadosDB.insert {
                it[AdvogadosDB.id] = model.advogado.id
                it[AdvogadosDB.nome] = model.advogado.nome
                it[AdvogadosDB.oab] = model.advogado.oab.numero
                it[AdvogadosDB.createdAt] = model.createdAt
            }
        }
    }

    override suspend fun delete(id: UUID) {
        transaction {
            AdvogadosDB.deleteWhere {
                AdvogadosDB.id eq id
            }
        }
    }

    override suspend fun update(model: AdvogadoWriteModel) {
        transaction {
            AdvogadosDB.update({ AdvogadosDB.id eq model.advogado.id }) {
                it[AdvogadosDB.nome] = model.advogado.nome
                it[AdvogadosDB.oab] = model.advogado.oab.numero
                it[AdvogadosDB.createdAt] = model.createdAt
            }
        }
    }

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
}

private fun ResultRow.asAdvogado() = Advogado(
    this[AdvogadosDB.id],
    this[AdvogadosDB.nome],
    OAB(this[AdvogadosDB.oab])
)