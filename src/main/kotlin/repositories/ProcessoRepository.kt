package repositories

import aggregates.Processo
import database.ProcessosDB
import models.write.processo.ProcessoWriteModel
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

interface IProcessoRepository : IRepository {
    suspend fun create(model: ProcessoWriteModel)

    suspend fun delete(id: UUID)
}

class ProcessoRepository : IProcessoRepository {

    override suspend fun create(model: ProcessoWriteModel) {
        transaction {
            ProcessosDB.insert {
                it[ProcessosDB.id] = model.id
                it[ProcessosDB.numero] = model.numero
                it[ProcessosDB.createdAt] = model.createdAt
                it[ProcessosDB.partes] = model.partes.joinToString(";")
            }
        }
    }

    override suspend fun delete(id: UUID) {
        transaction {
            ProcessosDB.deleteWhere { ProcessosDB.id eq id }
        }
    }
}