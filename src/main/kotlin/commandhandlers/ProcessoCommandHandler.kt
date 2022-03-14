package commandhandlers

import commands.processo.CriarProcesso
import commands.processo.DeletarProcesso
import models.write.processo.ProcessoWriteModel
import org.joda.time.DateTime
import repositories.ProcessoRepository
import services.ProcessoService

class ProcessoCommandHandler : ICommandHandler {

    private val service = ProcessoService(ProcessoRepository())

    suspend fun handle(cmd: CriarProcesso) {
        service.create(cmd.processo)
    }

    suspend fun handle(cmd: DeletarProcesso) {
        service.delete(cmd.id)
    }
}