package commandhandlers

import commands.processo.AtualizarProcesso
import commands.processo.CriarProcesso
import commands.processo.DeletarProcesso
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

    suspend fun handle(cmd: AtualizarProcesso) {
        service.update(cmd.processo)
    }
}