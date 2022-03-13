package entities

import valueobjects.OAB
import java.util.UUID

data class Advogado(
    val id: UUID,
    val nome: String,
    val oab: OAB
)