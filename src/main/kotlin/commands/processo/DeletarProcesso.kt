package commands.processo

import commands.ICommand
import java.util.UUID

data class DeletarProcesso(
    val id: UUID
) : ICommand