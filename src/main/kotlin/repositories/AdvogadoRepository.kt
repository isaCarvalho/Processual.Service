package repositories

import database.AdvogadosDB
import database.AdvogadosProcessosDB
import models.write.advogado.AdicionarAdvogadosWriteModel
import models.write.advogado.AdvogadoWriteModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

interface IAdvogadoRepository : IRepository {
    suspend fun create(model: AdvogadoWriteModel)

    suspend fun delete(id: UUID)

    suspend fun update(model: AdvogadoWriteModel)

    suspend fun addAdvogadoProcesso(model: AdicionarAdvogadosWriteModel)
}

class AdvogadoRepository : IAdvogadoRepository {

    override suspend fun create(model: AdvogadoWriteModel) {

        transaction {
            AdvogadosDB.insert {
                it[id] = model.id
                it[nome] = model.nome
                it[oab] = model.oab.numero
                it[createdAt] = model.createdAt
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
            AdvogadosDB.update({ AdvogadosDB.id eq model.id }) {
                it[nome] = model.nome
                it[oab] = model.oab.numero
                it[createdAt] = model.createdAt
            }
        }
    }

    override suspend fun addAdvogadoProcesso(model: AdicionarAdvogadosWriteModel) {
        transaction {
            model.advogados.forEach { adv ->
                AdvogadosProcessosDB.insert {
                    it[processoId] = model.processoId
                    it[advogadosId] = adv
                }
            }
        }
    }
}