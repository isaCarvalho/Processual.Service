import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.SerializationFeature
import commandhandlers.AdvogadoCommandHandler
import commandhandlers.ProcessoCommandHandler
import database.DB
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.Date
import database.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import queries.AdvogadoQuery
import queries.ProcessoQuery
import routers.advogadoRouter
import routers.processoRouter

fun main() {
    val port = 8080

    val server = embeddedServer(Netty, port, module = Application::mainModule)

    server.start()
}

fun Application.mainModule() {

    DB.connect()

    transaction {
        SchemaUtils.create(
            ProcessosDB,
            PrazoDB,
            AdvogadosDB,
            AdvogadosProcessosDB
        )
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    val secret = System.getenv("SECRET_KEY")

    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                .build()
            )

            validate { jwtCredential ->
                if (jwtCredential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(jwtCredential.payload)
                } else {
                    null
                }
            }
        }
    }

    routing {
        trace {
            application.log.trace(it.buildText())
        }

        get {
            context.respond(mapOf(Pair(Date(), "Bem-vindo ao Servico Processual.")))
        }

        authenticate("auth-jwt") {

            processoRouter(ProcessoCommandHandler(), ProcessoQuery())
            advogadoRouter(AdvogadoCommandHandler(), AdvogadoQuery())

        }
    }
}