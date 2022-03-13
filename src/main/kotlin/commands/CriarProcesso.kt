package commands

import aggregates.Processo
import org.joda.time.DateTime

class CriarProcesso (
    val processo: Processo,
    val createdAt: String
) : ICommand