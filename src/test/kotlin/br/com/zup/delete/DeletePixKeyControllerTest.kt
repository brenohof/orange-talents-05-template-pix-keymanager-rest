package br.com.zup.delete

import br.com.zup.RemoveChavePixRequestGRpc
import br.com.zup.RemoveChavePixResponseGRpc
import br.com.zup.RemovePixKeyGrpcServiceGrpc.RemovePixKeyGrpcServiceBlockingStub
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

@MicronautTest
class DeletePixKeyControllerTest(
    @Inject
    @field:Client("/")
    var restClient: HttpClient,
    val grpcClient: RemovePixKeyGrpcServiceBlockingStub
) {
    val clientId = UUID.randomUUID()
    val pixId = UUID.randomUUID()

    @BeforeEach
    internal fun setUp() {
        Mockito.`when`(grpcClient
            .removeChavePix(RemoveChavePixRequestGRpc.newBuilder()
            .setPixId(pixId.toString())
            .setClienteId(clientId.toString())
            .build()))
            .thenReturn(RemoveChavePixResponseGRpc.newBuilder()
                .setMessage("").build())
    }

    @Test
    internal fun `deve remover uma chave pix`() {
        val request = HttpRequest.DELETE("api/v1/clients/$clientId/pix/$pixId", null)
        val response = restClient.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNull(response.body())
    }
}