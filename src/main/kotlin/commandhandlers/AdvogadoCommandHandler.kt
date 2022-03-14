package commandhandlers

import commands.advogados.AdicionarAdvogadosAoProcesso
import commands.advogados.AtualizarAdvogado
import commands.advogados.CriarAdvogado
import commands.advogados.DeletarAdvogado
import models.write.advogado.AdvogadoWriteModel
import org.joda.time.DateTime
import queries.AdvogadoQuery
import repositories.AdvogadoRepository
import services.AdvogadoService
import java.util.UUID

class AdvogadoCommandHandler : ICommandHandler {

    private val service = AdvogadoService(AdvogadoRepository(), AdvogadoQuery())

    suspend fun handle(cmd: CriarAdvogado) {
        service.create(cmd.advogado)
    }

    suspend fun handle(cmd: AtualizarAdvogado) {
        service.update(cmd.advogado)
    }

    suspend fun handle(cmd: DeletarAdvogado) {
        service.delete(cmd.id)
    }

    suspend fun handle(cmd: AdicionarAdvogadosAoProcesso) {
        service.addAdvogadoProcesso(cmd.model)
    }

    suspend fun handle(id: UUID) = service.get(id)

    suspend fun handle(ids: List<UUID>) = service.getBatch(ids)

    suspend fun handle() = service.getAll()
}