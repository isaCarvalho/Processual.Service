package commandhandlers

import commands.CriarProcesso
import commands.DeletarProcesso
import models.write.ProcessoWriteModel
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