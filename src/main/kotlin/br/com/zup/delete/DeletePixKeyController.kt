package br.com.zup.delete

import br.com.zup.RemoveChavePixRequestGRpc
import br.com.zup.RemovePixKeyGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clients/{clientId}")
class DeletePixKeyController(val deletePixKeyClient: RemovePixKeyGrpcServiceGrpc.RemovePixKeyGrpcServiceBlockingStub) {

    val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Delete("/pix/{pixId}")
    fun delete(@PathVariable clientId: UUID, @PathVariable pixId: UUID): HttpResponse<Any> {
        deletePixKeyClient.removeChavePix(RemoveChavePixRequestGRpc.newBuilder()
            .setClienteId(clientId.toString())
            .setPixId(pixId.toString())
            .build())

        LOGGER.info("Removida com sucesso.")

        return HttpResponse.noContent()
    }
}