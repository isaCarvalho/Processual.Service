package models.write.advogado

import entities.Advogado

data class AdvogadoWriteModel(
    val advogado: Advogado,
    val createdAt: String
)