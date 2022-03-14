package models.write.advogado

import java.util.UUID

data class AdicionarAdvogadosWriteModel(
    val advogados: List<UUID>,
    val processoId: UUID,
    val createdAt: String
)