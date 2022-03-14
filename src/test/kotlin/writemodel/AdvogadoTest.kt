package writemodel

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import commands.advogados.AdicionarAdvogadosAoProcesso
import commands.advogados.AtualizarAdvogado
import commands.advogados.CriarAdvogado
import commands.advogados.DeletarAdvogado
import commands.processo.CriarProcesso
import database.AdvogadosDB
import database.AdvogadosProcessosDB
import database.DB
import database.PrazoDB
import entities.Advogado
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import mainModule
import models.write.advogado.AdicionarAdvogadosWriteModel
import models.write.advogado.AdvogadoWriteModel
import models.write.processo.ProcessoWriteModel
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

    @Test
    fun adicionarAoProcesso() {
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

            // given
            val processoId = UUID.randomUUID()
            val cmdCriarProcesso = CriarProcesso(
                ProcessoWriteModel(
                id = processoId,
                numero = 1,
                partes = listOf("p1"),
                createdAt = DateTime.now().toString()
                )
            )

            val callCriarProcesso = create(cmdCriarProcesso, "/processo")
            Assert.assertEquals(callCriarProcesso.response.status(), HttpStatusCode.Created)

            // when
            val cmdAtualizar = AdicionarAdvogadosAoProcesso(AdicionarAdvogadosWriteModel(
                processoId = processoId,
                advogados = listOf(advogado.id),
                createdAt = DateTime.now().toString()
            ))

            val updateCall = adicionarAoProcesso(cmdAtualizar, baseUri)

            // then
            Assert.assertEquals(updateCall.response.status(), HttpStatusCode.OK)
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

fun TestApplicationEngine.adicionarAoProcesso(cmd : AdicionarAdvogadosAoProcesso, baseUri: String) = handleRequest(HttpMethod.Put, "$baseUri/adicionarAoProcesso") {
    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    setBody(jacksonObjectMapper().writeValueAsBytes(cmd))
}

fun TestApplicationEngine.delete(cmd : DeletarAdvogado, baseUri: String) = handleRequest(HttpMethod.Delete, baseUri) {
    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    setBody(jacksonObjectMapper().writeValueAsBytes(cmd))
}