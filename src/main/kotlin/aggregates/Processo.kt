package aggregates

import entities.Advogado
import entities.Prazo
import valueobjects.Rito
import java.util.UUID

data class Processo(
    val id: UUID,
    val numero: Int,
    val rito: Rito,
    val partes: List<String>,
    val instancia: Int,
    val advogados: List<Advogado>,
    val prazo: Prazo
) : IAggregate