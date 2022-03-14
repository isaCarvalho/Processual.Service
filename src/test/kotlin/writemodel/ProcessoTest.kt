package writemodel

import aggregates.Processo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import commands.processo.CriarProcesso
import commands.processo.DeletarProcesso
import database.*
import entities.Advogado
import entities.Prazo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import mainModule
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import valueobjects.OAB
import valueobjects.Rito
import java.util.*

class ProcessoTest {

    private val baseUri = "/processo"
    private val processo = Processo(
        id = UUID.randomUUID(),
        numero = 120490340,
        rito = Rito("Cabo Frio/RJ", "3 vara civel", UUID.randomUUID()),
        partes = mutableListOf("Pt 1", "Pt 2"),
        advogados = mutableListOf(Advogado(UUID.randomUUID(), "Adv 1", OAB(12))),
        instancia = 1,
        prazo = Prazo(UUID.randomUUID(), 1, DateTime().toString())
    )

    @Test
    fun create() {
        withTestApplication(Application::mainModule) {
            // given
            val cmd = CriarProcesso(processo, DateTime().toString())

            // when
            val call = create(cmd, baseUri)

            // then
            Assert.assertEquals(call.response.status(), HttpStatusCode.Created)
        }
    }

    @Test
    fun delete() {
        withTestApplication(Application::mainModule) {
            // given
            val cmd = CriarProcesso(processo, DateTime().toString())
            val call = create(cmd, baseUri)

            Assert.assertEquals(call.response.status(), HttpStatusCode.Created)

            // when
            val deleteCall = delete(DeletarProcesso(processo.id), baseUri)

            // then
            Assert.assertEquals(deleteCall.response.status(), HttpStatusCode.OK)
        }
    }

    @Before
    fun before() {
        DB.connect()
        transaction {
            SchemaUtils.drop(
                AdvogadosProcessosDB,
                ProcessosDB,
                PrazoDB,
                AdvogadosDB,
            )
        }
    }
}

fun TestApplicationEngine.create(cmd : CriarProcesso, baseUri: String) = handleRequest(HttpMethod.Post, baseUri) {
    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    setBody(jacksonObjectMapper().writeValueAsBytes(cmd))
}

fun TestApplicationEngine.delete(cmd : DeletarProcesso, baseUri: String) = handleRequest(HttpMethod.Delete, baseUri) {
    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    setBody(jacksonObjectMapper().writeValueAsBytes(cmd))
}