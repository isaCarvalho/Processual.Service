package commands.processo

import aggregates.Processo
import commands.ICommand

class CriarProcesso (
    val processo: Processo,
    val createdAt: String
) : ICommand