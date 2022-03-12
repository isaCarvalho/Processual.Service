package commands

import java.util.UUID

data class DeletarProcesso(
    val id: UUID
) : ICommand