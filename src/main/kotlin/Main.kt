import com.fasterxml.jackson.databind.ObjectMapper
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

    routing {
        trace {
            application.log.trace(it.buildText())
        }

        get {
            context.respond(mapOf(Pair(Date(), "Bem-vindo ao Servico Processual.")))
        }

        processoRouter(ProcessoCommandHandler())
        advogadoRouter(AdvogadoCommandHandler())
    }
}

fun String.asJson() = ObjectMapper().readTree(this)