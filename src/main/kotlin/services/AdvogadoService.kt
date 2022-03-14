package services

import entities.Advogado
import models.write.advogado.AdvogadoWriteModel
import repositories.AdvogadoRepository
import java.util.*

class AdvogadoService(private val repository: AdvogadoRepository) {
    suspend fun create(model: AdvogadoWriteModel) {
        repository.create(model)
    }

    suspend fun delete(id: UUID) {
        repository.delete(id)
    }

    suspend fun update(model: AdvogadoWriteModel) {
        repository.update(model)
    }


    suspend fun get(id: UUID): Advogado? = repository.get(id)

    suspend fun getBatch(ids: List<UUID>) : List<Advogado> = repository.getBatch(ids)
}