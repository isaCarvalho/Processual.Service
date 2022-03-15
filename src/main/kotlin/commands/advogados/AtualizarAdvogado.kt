package commands.advogados

import commands.ICommand
import models.write.advogado.AdvogadoWriteModel

data class AtualizarAdvogado(
    val model: AdvogadoWriteModel
) : ICommand