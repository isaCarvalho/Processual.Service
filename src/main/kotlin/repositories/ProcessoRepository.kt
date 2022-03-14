package repositories

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
                it[ProcessosDB.id] = model.processo.id
                it[ProcessosDB.numero] = model.processo.numero
                it[ProcessosDB.comarca] = model.processo.rito.comarca
                it[ProcessosDB.vara] = model.processo.rito.vara
                it[ProcessosDB.competenciaId] = model.processo.rito.competenciaId
                it[ProcessosDB.instancia] = model.processo.instancia
                it[ProcessosDB.createdAt] = model.createdAt
            }
        }
    }

    override suspend fun delete(id: UUID) {
        transaction {
            ProcessosDB.deleteWhere { ProcessosDB.id eq id }
        }
    }
}