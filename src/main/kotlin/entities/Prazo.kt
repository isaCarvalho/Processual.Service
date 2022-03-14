package entities

import java.util.Date
import java.util.UUID

data class Prazo(
    val id: UUID,
    val quantidadeEmDias: Int,
    val createdAt: String
) : IEntity