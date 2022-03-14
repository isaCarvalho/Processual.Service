package models.write.advogado

import valueobjects.OAB
import java.util.*

data class AdvogadoWriteModel(
    val id: UUID,
    val nome: String,
    val oab: OAB,
    val createdAt: String
)