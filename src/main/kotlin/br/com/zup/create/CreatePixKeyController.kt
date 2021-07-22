package br.com.zup.create

import br.com.zup.NovaChavePixResponseGRpc
import br.com.zup.PixKeyManagerGrpcServiceGrpc
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.validation.Valid

@Validated
@Controller
class CreatePixKeyController(val grpcClient: PixKeyManagerGrpcServiceGrpc.PixKeyManagerGrpcServiceBlockingStub) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Post("/api/keys")
    fun create(@Valid @Body request: NewPixKeyRequest): HttpResponse<Any> {

        var response: NovaChavePixResponseGRpc? = null

        try {
            response = grpcClient.novaChavePix(request.toModel())
        } catch (e: StatusRuntimeException) {
            val statusCode = e.status.code
            val description = e.status.description
            if (statusCode == Status.Code.INVALID_ARGUMENT ||
                statusCode == Status.Code.FAILED_PRECONDITION) {
                throw HttpStatusException(HttpStatus.BAD_REQUEST, description)
            }

            if (statusCode == Status.Code.ALREADY_EXISTS) {
                throw HttpStatusException(HttpStatus.UNPROCESSABLE_ENTITY, description)
            }
        }

        logger.info("Registro bem sucedido $response")

        val uri = UriBuilder.of("/api/keys/{id}")
            .expand(mutableMapOf(Pair("id", response?.pixId)))

        return HttpResponse.created(uri)
    }
}