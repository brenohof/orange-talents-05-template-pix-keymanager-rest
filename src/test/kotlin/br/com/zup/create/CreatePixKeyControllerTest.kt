package br.com.zup.create

import br.com.zup.NovaChavePixRequestGRpc
import br.com.zup.NovaChavePixResponseGRpc
import br.com.zup.PixKeyManagerGrpcServiceGrpc.PixKeyManagerGrpcServiceBlockingStub
import br.com.zup.shared.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
class CreatePixKeyControllerTest(
    @Inject
    @field:Client("/")
    var restClient: HttpClient,
    val grpcClient: PixKeyManagerGrpcServiceBlockingStub
) {
    val clientId = UUID.randomUUID()
    val pixId = UUID.randomUUID().toString()

    @BeforeEach
    internal fun setUp() {
        val request = novaChavePixRequestGRpc()

        Mockito.`when`(grpcClient.novaChavePix(request))
            .thenReturn(NovaChavePixResponseGRpc.newBuilder()
                .setPixId(pixId).build())
    }

    @Test
    internal fun `deve cadastrar uma nova chave`() {
        val newPixKey = newPixKeyRequest()

        val request = HttpRequest.POST("api/v1/clients/$clientId/pix", newPixKey)
        val response = restClient.toBlocking().exchange(request, NewPixKeyRequest::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains(pixId))
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock(): PixKeyManagerGrpcServiceBlockingStub {
            return Mockito.mock(PixKeyManagerGrpcServiceBlockingStub::class.java)
        }
    }

    private fun novaChavePixRequestGRpc(): NovaChavePixRequestGRpc {
        return newPixKeyRequest().toModel(clientId)
    }

    private fun newPixKeyRequest() = NewPixKeyRequest(
        keyType = KeyType.EMAIL,
        keyValue = "test@email.com",
        accountType = AccountType.CONTA_CORRENTE
    )
}