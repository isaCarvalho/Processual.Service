package routers

import commandhandlers.AdvogadoCommandHandler
import commands.advogados.AtualizarAdvogado
import commands.advogados.CriarAdvogado
import commands.advogados.DeletarAdvogado
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.routing.*

fun Route.advogadoRouter(handler: AdvogadoCommandHandler) {
    route("/advogado") {
        post {
            with(call) {
                val cmd = receive<CriarAdvogado>()

                handler.handle(cmd)
                call.response.status(HttpStatusCode.Created)
            }
        }

        put {
            with(call) {
                val cmd = receive<AtualizarAdvogado>()

                handler.handle(cmd)
                call.response.status(HttpStatusCode.OK)
            }
        }

        delete {
            with(call) {
                val cmd = receive<DeletarAdvogado>()

                handler.handle(cmd)
                call.response.status(HttpStatusCode.OK)
            }
        }
    }
}