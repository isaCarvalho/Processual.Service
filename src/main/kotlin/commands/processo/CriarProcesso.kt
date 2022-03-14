package commands.processo

import commands.ICommand
import models.write.processo.ProcessoWriteModel

class CriarProcesso (
    val processo: ProcessoWriteModel
) : ICommand