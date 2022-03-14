package commandhandlers

import commands.processo.CriarProcesso
import commands.processo.DeletarProcesso
import models.write.processo.ProcessoWriteModel
import repositories.ProcessoRepository
import services.ProcessoService

class ProcessoCommandHandler : ICommandHandler {

    private val service = ProcessoService(ProcessoRepository())

    suspend fun handle(cmd: CriarProcesso) {
        val model = ProcessoWriteModel(cmd.processo, cmd.createdAt)
        service.create(model)
    }

    suspend fun handle(cmd: DeletarProcesso) {
        service.delete(cmd.id)
    }
}