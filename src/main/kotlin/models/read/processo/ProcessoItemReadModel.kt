package models.read.processo

import models.read.IReadModel
import valueobjects.OAB
import java.util.*

data class ProcessoItemReadModel(
    val id: UUID,
    val numero: Int,
    val advogados: MutableMap<OAB, String>,
): IReadModel