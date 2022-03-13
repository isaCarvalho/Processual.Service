package models.write

import aggregates.Processo
import org.joda.time.DateTime
import java.util.*

data class ProcessoWriteModel (
    val processo: Processo,
    val createdAt: String
) : IWriteModel