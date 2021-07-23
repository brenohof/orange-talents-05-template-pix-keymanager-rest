package br.com.zup.create

import br.com.zup.NovaChavePixResponseGRpc
import br.com.zup.PixKeyManagerGrpcServiceGrpc
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clients/{clientId}")
class CreatePixKeyController(val createPixKeyClient: PixKeyManagerGrpcServiceGrpc.PixKeyManagerGrpcServiceBlockingStub) {

    val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Post("/pix")
    fun create(
        @Valid @Body request: NewPixKeyRequest,
        @PathVariable clientId: UUID
    ): HttpResponse<Any> {
        val response = createPixKeyClient.novaChavePix(request.toModel(clientId))

        LOGGER.info("Registro nova chave pix bem sucedido $response")

        return HttpResponse.created(location(clientId, response.pixId))
    }

    private fun location(clientId: UUID, pixId: String): URI {
        return HttpResponse.uri("/api/v1/clientes/$clientId/pix/$pixId")
    }
}