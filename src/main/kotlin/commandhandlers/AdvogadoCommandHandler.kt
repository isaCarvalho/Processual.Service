package commandhandlers

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
        val model = AdvogadoWriteModel(cmd.advogado, DateTime.now().toString())
        service.create(model)
    }

    suspend fun handle(cmd: AtualizarAdvogado) {
        val model = AdvogadoWriteModel(cmd.advogado, DateTime.now().toString())
        service.update(model)
    }

    suspend fun handle(cmd: DeletarAdvogado) {
        service.delete(cmd.id)
    }
}