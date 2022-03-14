package commands.advogados

import commands.ICommand
import entities.Advogado

data class AtualizarAdvogado(
    val advogado: Advogado
) : ICommand