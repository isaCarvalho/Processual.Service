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
import io.ktor.request.*
import io.ktor.server.testing.*
import mainModule
import models.write.processo.ProcessoWriteModel
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
            val cmd = CriarProcesso(ProcessoWriteModel(
                id = processo.id,
                numero = processo.numero,
                partes = processo.partes,
                createdAt = DateTime.now().toString()
            ))

            // when
            val call = create(cmd, baseUri)

            // then
            Assert.assertEquals(HttpStatusCode.Created, call.response.status())
        }
    }

    @Test
    fun delete() {
        withTestApplication(Application::mainModule) {
            // given
            val cmd = CriarProcesso(ProcessoWriteModel(
                id = processo.id,
                numero = processo.numero,
                partes = processo.partes,
                createdAt = DateTime.now().toString()
            ))
            val call = create(cmd, baseUri)

            Assert.assertEquals(HttpStatusCode.OK, call.response.status())

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
    addHeader(HttpHeaders.Authorization, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDgxMzA1OTYsInVzZXJuYW1lIjoiSXNhYmVsYSJ9.rNwbCPCvxT6_VCWNzYVnlzh8ZYdc_zpfzBlw5LBww6Y")
    setBody(jacksonObjectMapper().writeValueAsBytes(cmd))
}

fun TestApplicationEngine.delete(cmd : DeletarProcesso, baseUri: String) = handleRequest(HttpMethod.Delete, baseUri) {
    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    addHeader(HttpHeaders.Authorization, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDgxMzAyMjAsInVzZXJuYW1lIjoiSXNhYmVsYSJ9.HcjmYtNLnQuRQVNoQMpGl8YKTa5y9KX48sp5SXSeGR4")
    setBody(jacksonObjectMapper().writeValueAsBytes(cmd))
}