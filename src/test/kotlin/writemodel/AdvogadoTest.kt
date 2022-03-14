package writemodel

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import commands.advogados.AtualizarAdvogado
import commands.advogados.CriarAdvogado
import commands.advogados.DeletarAdvogado
import database.AdvogadosDB
import database.AdvogadosProcessosDB
import database.DB
import database.PrazoDB
import entities.Advogado
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import mainModule
import models.write.advogado.AdvogadoWriteModel
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import valueobjects.OAB
import java.util.*

class AdvogadoTest {

    private val baseUri = "/advogado"
    private val advogado = Advogado(UUID.randomUUID(), "Adv 1", OAB(12))

    @Test
    fun create() {
        withTestApplication(Application::mainModule) {
            // given
            val cmd = CriarAdvogado(AdvogadoWriteModel(
                id = advogado.id,
                nome = advogado.nome,
                oab = advogado.oab,
                createdAt = DateTime.now().toString()
            ))

            // when
            val call = create(cmd, baseUri)

            // then
            Assert.assertEquals(call.response.status(), HttpStatusCode.Created)
        }
    }

    @Test
    fun update() {
        withTestApplication(Application::mainModule) {
            // given
            val cmd = CriarAdvogado(AdvogadoWriteModel(
                id = advogado.id,
                nome = advogado.nome,
                oab = advogado.oab,
                createdAt = DateTime.now().toString()
            ))
            val call = create(cmd, baseUri)

            Assert.assertEquals(call.response.status(), HttpStatusCode.Created)

            // when
            val cmdAtualizar = AtualizarAdvogado(AdvogadoWriteModel(
                id = advogado.id,
                nome = advogado.nome,
                oab = advogado.oab,
                createdAt = DateTime.now().toString()
            ))
            val updateCall = update(cmdAtualizar, baseUri)

            // then
            Assert.assertEquals(updateCall.response.status(), HttpStatusCode.OK)
        }
    }

    @Test
    fun delete() {
        withTestApplication(Application::mainModule) {
            // given
            val cmd = CriarAdvogado(AdvogadoWriteModel(
                id = advogado.id,
                nome = advogado.nome,
                oab = advogado.oab,
                createdAt = DateTime.now().toString()
            ))
            val call = create(cmd, baseUri)

            Assert.assertEquals(call.response.status(), HttpStatusCode.Created)

            // when
            val deleteCall = delete(DeletarAdvogado(advogado.id), baseUri)

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
                AdvogadosDB,
                PrazoDB,
                AdvogadosDB,
            )
        }
    }
}

fun TestApplicationEngine.create(cmd : CriarAdvogado, baseUri: String) = handleRequest(HttpMethod.Post, baseUri) {
    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    setBody(jacksonObjectMapper().writeValueAsBytes(cmd))
}

fun TestApplicationEngine.update(cmd : AtualizarAdvogado, baseUri: String) = handleRequest(HttpMethod.Put, baseUri) {
    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    setBody(jacksonObjectMapper().writeValueAsBytes(cmd))
}

fun TestApplicationEngine.delete(cmd : DeletarAdvogado, baseUri: String) = handleRequest(HttpMethod.Delete, baseUri) {
    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    setBody(jacksonObjectMapper().writeValueAsBytes(cmd))
}