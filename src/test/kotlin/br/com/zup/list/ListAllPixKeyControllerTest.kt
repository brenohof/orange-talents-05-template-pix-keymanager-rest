package br.com.zup.list

import br.com.zup.*
import com.google.protobuf.Timestamp
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.inject.Inject
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.hasSize

@MicronautTest
class DetailPixKeyControllerTest(
    @Inject
    @field:Client("/")
    var restClient: HttpClient,
    val grpcClient: ListarTodasPixKeyGrpcServiceGrpc.ListarTodasPixKeyGrpcServiceBlockingStub
) {
    val clientId = UUID.randomUUID()
    val pixId = UUID.randomUUID()

    @BeforeEach
    internal fun setUp() {
        Mockito.`when`(listaChavePixRequest())
            .thenReturn(listaChavePixResponse())
    }

    @Test
    internal fun `deve detalhar uma chave pix`() {
        val request = HttpRequest.GET<Any>("/api/v1/clients/$clientId/pix")
        val response = restClient.toBlocking().exchange(request, List::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertThat(response.body(), hasSize(5))
        assertTrue(response.body() is List<*>)
    }

    private fun listaChavePixRequest() = grpcClient.listaTodasChavePix(
        ListaTodasChavePixRequest.newBuilder()
                .setClienteId(clientId.toString())
                .build()
    )

    private fun listaChavePixResponse() = ListaTodasChavePixResponse.newBuilder()
        .setClienteId(clientId.toString())
        .addAllChaves(listOf(pixKeyResponse(), pixKeyResponse()
            , pixKeyResponse(), pixKeyResponse(), pixKeyResponse()))
        .build()

    private fun pixKeyResponse() = PixKeyResponse.newBuilder()
        .setPixId(pixId.toString())
        .setClienteId(clientId.toString())
        .setChave(UUID.randomUUID().toString())
        .setTipoDaChave(TipoDaChave.CHAVE_ALEATORIA)
        .setTipoDaConta(TipoDaConta.CONTA_POUPANCA)
        .setCriadaEm(LocalDateTime.now().toInstant(ZoneOffset.UTC).let {
            Timestamp.newBuilder()
                .setSeconds(it.epochSecond)
                .setNanos(it.nano)
                .build()
        })
        .build()
}