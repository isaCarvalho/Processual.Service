package routers

import commandhandlers.ProcessoCommandHandler
import commands.CriarProcesso
import commands.DeletarProcesso
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.routing.*

fun Route.processoRouter(handler: ProcessoCommandHandler) {
    route("/processo") {
        post {
            with(call) {
                val cmd = receive<CriarProcesso>()

                handler.handle(cmd)
                call.response.status(HttpStatusCode.Created)
            }
        }

        delete {
            with(call) {
                val cmd = receive<DeletarProcesso>()

                handler.handle(cmd)
                call.response.status(HttpStatusCode.OK)
            }
        }
    }
}