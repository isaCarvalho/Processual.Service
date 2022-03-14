package models.write.processo

import models.write.IWriteModel
import java.util.*

data class ProcessoWriteModel (
    val id: UUID,
    val numero: Int,
    val partes: List<String>,
    val createdAt: String
) : IWriteModel