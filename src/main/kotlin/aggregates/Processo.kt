package aggregates

import entities.Prazo
import valueobjects.OAB
import valueobjects.Rito
import java.util.UUID

data class Processo(
    val id: UUID,
    val numero: Int,
    val rito: Rito,
    val partes: MutableList<String>,
    val advogados: MutableMap<OAB, String>,
    val instancia: Int,
    val prazo: Prazo
) : IAggregate