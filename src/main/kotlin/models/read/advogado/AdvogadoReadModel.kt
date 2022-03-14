package models.read.advogado

import models.read.IReadModel
import valueobjects.OAB
import java.util.*

data class AdvogadoReadModel(
    val id: UUID,
    val nome: String,
    val oab: OAB
) : IReadModel