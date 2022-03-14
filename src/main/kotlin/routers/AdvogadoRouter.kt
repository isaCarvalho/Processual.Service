package routers

import commandhandlers.AdvogadoCommandHandler
import commands.advogados.AdicionarAdvogadosAoProcesso
import commands.advogados.AtualizarAdvogado
import commands.advogados.CriarAdvogado
import commands.advogados.DeletarAdvogado
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

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

        put("/adicionarAoProcesso") {
            with(call) {
                val cmd = receive<AdicionarAdvogadosAoProcesso>()

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

        get("/{id}") {
            with(call) {
                val id = UUID.fromString(parameters["id"])
                val adv = handler.handle(id)

                if (adv == null) {
                    respond(HttpStatusCode.NotFound)
                } else {
                    respond(adv)
                }
            }
        }

        get("/batch") {
            with(call) {
                val ids = receive<List<UUID>>()
                val advs = handler.handle(ids)

                respond(advs)
            }
        }

        get {
            with(call) {
                respond(handler.handle())
            }
        }
    }
}