package models.read.processo

import models.read.IReadModel
import java.util.*

data class ProcessoReadModel(
    val id: UUID,
    val numero: Int,
    val partes: List<String>
) : IReadModel