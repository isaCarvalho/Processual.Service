package commands.advogados

import commands.ICommand
import entities.Advogado
import java.util.UUID

data class AdicionarAdvogadosAoProcesso(
    val processoId: UUID,
    val advogadosIds: List<UUID>
) : ICommand