package routers

import commandhandlers.ProcessoCommandHandler
import commands.processo.AtualizarProcesso
import commands.processo.CriarProcesso
import commands.processo.DeletarProcesso
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import queries.ProcessoQuery
import java.util.*

fun Route.processoRouter(handler: ProcessoCommandHandler, query: ProcessoQuery) {
    route("/processo") {
        post {
            with(call) {
                val cmd = receive<CriarProcesso>()

                handler.handle(cmd)
                call.response.status(HttpStatusCode.Created)
            }
        }

        put {
            with(call) {
                val cmd = receive<AtualizarProcesso>()

                handler.handle(cmd)
                call.response.status(HttpStatusCode.OK)
            }
        }

        delete {
            with(call) {
                val cmd = receive<DeletarProcesso>()

                handler.handle(cmd)
                call.response.status(HttpStatusCode.OK)
            }
        }

        get("/{id}") {
            with(call) {
                val id = UUID.fromString(parameters["id"])
                val adv = query.get(id)

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
                val advs = query.getBatch(ids)

                respond(advs)
            }
        }

        get {
            with(call) {
                respond(query.getAll())
            }
        }
    }
}