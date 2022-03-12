package models.read

import valueobjects.OAB
import valueobjects.Rito
import java.util.*

data class ProcessoCompletoReadModel(
    val id: UUID,
    val numero: Int,
    val rito: Rito,
    val partes: MutableList<String>,
    val advogados: MutableMap<OAB, String>,
    val instancia: Int,
) : IReadModel