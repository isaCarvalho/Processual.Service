package commands.advogados

import commands.ICommand
import models.write.advogado.AdvogadoWriteModel

data class CriarAdvogado(
    val advogado: AdvogadoWriteModel
) : ICommand