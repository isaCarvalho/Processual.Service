package models.write.processo

import aggregates.Processo
import models.write.IWriteModel

data class ProcessoWriteModel (
    val processo: Processo,
    val createdAt: String
) : IWriteModel