package commands.advogados

import commands.ICommand
import entities.Advogado

data class CriarAdvogado(
    val advogado: Advogado
) : ICommand