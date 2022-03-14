package commands.advogados

import commands.ICommand
import java.util.UUID

data class DeletarAdvogado(
    val id: UUID
) : ICommand
