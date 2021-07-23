package br.com.zup.shared

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StatusRuntimeExceptionHandlerTest {

    private val request = HttpRequest.GET<Any>("/")

    @Test
    internal fun `deve retornar 404 quando statusExcetion for NOT FOUND`() {
        val message = "nao encontrado"
        val notFoundException = StatusRuntimeException(Status.NOT_FOUND.withDescription(message))

        val response = StatusRuntimeExceptionHandler().handle(request, notFoundException)

        assertEquals(HttpStatus.NOT_FOUND, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 422 quando statusExcetion for ALREADY EXISTS`() {
        val message = "cliente já existente."
        val unprocessableEntityException = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(message))

        val response = StatusRuntimeExceptionHandler().handle(request, unprocessableEntityException)

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 400 quando statusExcetion for INVALID ARGUMENT`() {
        val badRequestException = StatusRuntimeException(Status.INVALID_ARGUMENT)

        val response = StatusRuntimeExceptionHandler().handle(request, badRequestException)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertNotNull(response.body())
        assertEquals("Dados da requisição estão inválidos.", (response.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 400 quando statusExcetion for FAILED PRECONDITION`() {
        val message = "Alguma pre condição falhou, cliente não existe ou chave não existe no bcb."
        val badRequestException = StatusRuntimeException(Status.FAILED_PRECONDITION.withDescription(message))

        val response = StatusRuntimeExceptionHandler().handle(request, badRequestException)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 500 quando statusExcetion for desconhecida`() {
        val internalException = StatusRuntimeException(Status.UNKNOWN)

        val response = StatusRuntimeExceptionHandler().handle(request, internalException)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)
        assertNotNull(response.body())
        assertEquals("Não foi possível completar a requisição.", (response.body() as JsonError).message)
    }
}