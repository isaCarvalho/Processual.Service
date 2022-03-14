package services

import entities.Advogado
import models.write.advogado.AdicionarAdvogadosWriteModel
import models.write.advogado.AdvogadoWriteModel
import queries.AdvogadoQuery
import repositories.AdvogadoRepository
import java.util.*

class AdvogadoService(private val repository: AdvogadoRepository, private val query: AdvogadoQuery) {
    suspend fun create(model: AdvogadoWriteModel) {
        repository.create(model)
    }

    suspend fun delete(id: UUID) {
        repository.delete(id)
    }

    suspend fun update(model: AdvogadoWriteModel) {
        repository.update(model)
    }

    suspend fun get(id: UUID): Advogado? = query.get(id)

    suspend fun getAll() = query.getAll()

    suspend fun getBatch(ids: List<UUID>) : List<Advogado> = query.getBatch(ids)

    suspend fun addAdvogadoProcesso(model: AdicionarAdvogadosWriteModel) {
        repository.addAdvogadoProcesso(model)
    }
}