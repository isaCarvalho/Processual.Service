package commandhandlers

import commands.advogados.AdicionarAdvogadosAoProcesso
import commands.advogados.AtualizarAdvogado
import commands.advogados.CriarAdvogado
import commands.advogados.DeletarAdvogado
import models.write.advogado.AdvogadoWriteModel
import org.joda.time.DateTime
import repositories.AdvogadoRepository
import services.AdvogadoService

class AdvogadoCommandHandler : ICommandHandler {

    private val service = AdvogadoService(AdvogadoRepository())

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
}