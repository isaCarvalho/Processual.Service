package valueobjects

import java.util.UUID

data class Rito(
    val comarca: String,
    val vara: String,
    val competenciaId: UUID
)