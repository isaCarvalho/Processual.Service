package services

import models.write.processo.ProcessoWriteModel
import repositories.ProcessoRepository
import java.util.UUID

class ProcessoService(private val repository: ProcessoRepository) {

    suspend fun create(model: ProcessoWriteModel) {
        repository.create(model)
    }

    suspend fun update(model: ProcessoWriteModel) {
        repository.create(model)
    }

    suspend fun delete(id: UUID) {
        repository.delete(id)
    }
}