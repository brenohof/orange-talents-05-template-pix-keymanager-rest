package br.com.zup.list

import br.com.zup.ListaTodasChavePixRequest
import br.com.zup.ListarTodasPixKeyGrpcServiceGrpc
import br.com.zup.shared.toModel
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clients/{clientId}")
class ListAllPixKeyController(val listAllPixKeyClient: ListarTodasPixKeyGrpcServiceGrpc.ListarTodasPixKeyGrpcServiceBlockingStub) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/pix")
    fun listAll(@PathVariable clientId: UUID): HttpResponse<List<ClientPixKeyResponse>> {
        val response = listAllPixKeyClient.listaTodasChavePix(ListaTodasChavePixRequest.newBuilder()
            .setClienteId(clientId.toString())
            .build())

        LOGGER.info("Listagem de todas as chaves do cliente $clientId bem sucedida!")

        return HttpResponse.ok(response.toModel())
    }
}