package commands.advogados

import commands.ICommand
import entities.Advogado
import models.write.advogado.AdicionarAdvogadosWriteModel
import java.util.UUID

data class AdicionarAdvogadosAoProcesso(
    val model: AdicionarAdvogadosWriteModel
) : ICommand