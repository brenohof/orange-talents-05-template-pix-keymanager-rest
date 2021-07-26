package br.com.zup.detail

import br.com.zup.*
import br.com.zup.ListaPixKeyGrpcServiceGrpc.ListaPixKeyGrpcServiceBlockingStub
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

@MicronautTest
class DetailPixKeyControllerTest(
    @Inject
    @field:Client("/")
    var restClient: HttpClient,
    val grpcClient: ListaPixKeyGrpcServiceBlockingStub
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
        val request = HttpRequest.GET<Any>("/api/v1/clients/$clientId/pix/$pixId")
        val response = restClient.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
    }

    private fun listaChavePixRequest() = grpcClient.listaChavePix(
        ListaChavePixRequest.newBuilder()
            .setPixId(
                ListaChavePixRequest.FiltroPorPixId.newBuilder()
                    .setPixId(pixId.toString())
                    .setClienteId(clientId.toString())
                    .build()
            ).build()
    )


    private fun listaChavePixResponse() = ListaChavePixResponse.newBuilder()
        .setPixId(pixId.toString())
        .setClienteId(clientId.toString())
        .setChave(UUID.randomUUID().toString())
        .setTipoDaChave(TipoDaChave.CHAVE_ALEATORIA)
        .setConta(
            ListaChavePixResponse.Conta.newBuilder()
                .setNomeDaInstitucao("ITAU UNIBANCO S.A")
                .setAgencia("0001")
                .setNumero("10298383")
                .setTipo(TipoDaConta.CONTA_CORRENTE)
                .build()
        )
        .setTitular(
            ListaChavePixResponse.Titular.newBuilder()
                .setNome("Rafael Ponte")
                .setCpf("26366214077")
                .build()
        )
        .setCriadaEm(LocalDateTime.now().toInstant(ZoneOffset.UTC).let {
            Timestamp.newBuilder()
                .setSeconds(it.epochSecond)
                .setNanos(it.nano)
                .build()
        })
        .build()
}