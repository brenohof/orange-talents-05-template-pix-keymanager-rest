package br.com.zup.detail

import br.com.zup.ListaChavePixRequest
import br.com.zup.ListaChavePixRequest.FiltroPorPixId
import br.com.zup.ListaPixKeyGrpcServiceGrpc
import br.com.zup.shared.toModel
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clients/{clientId}")
class DetailPixKeyController(val detailPixKeyClient: ListaPixKeyGrpcServiceGrpc.ListaPixKeyGrpcServiceBlockingStub) {

    val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{pixId}")
    fun detail(@PathVariable clientId: UUID, @PathVariable pixId: UUID): HttpResponse<DetailPixKeyResponse> {
        val response = detailPixKeyClient.listaChavePix(ListaChavePixRequest.newBuilder()
            .setPixId(FiltroPorPixId.newBuilder()
                .setClienteId(clientId.toString())
                .setPixId(pixId.toString()).build())
            .build()).toModel()

        LOGGER.info("Detalhes da chave pix bem sucedido!")

        return HttpResponse.ok(response)
    }
}