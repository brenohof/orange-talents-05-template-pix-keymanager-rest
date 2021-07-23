package br.com.zup.create

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class NewPixKeyRequestTest {

    @Test
    internal fun `deve retornar true para cpf valido`() {
        with(KeyType.CPF) {
            assertTrue(this.validate("83422523022"))
        }
    }

    @Test
    internal fun `deve retornar false para cpf invalido`() {
        with(KeyType.CPF) {
            assertFalse(this.validate("83422523023"))
        }
    }

    @Test
    internal fun `deve retornar false para cpf no formato invalido`() {
        with(KeyType.CPF) {
            assertFalse(this.validate("cpf no formato invalido"))
            assertFalse(this.validate("000"))
            assertFalse(this.validate("834.225.230-22"))
        }
    }

    @Test
    internal fun `deve retornar false para cpf nulo ou vazio`() {
        with(KeyType.CPF) {
            assertFalse(this.validate(""))
            assertFalse(this.validate(null))
        }
    }

    @Test
    internal fun `deve retornar true para telefone valido`() {
        with(KeyType.PHONE) {
            assertTrue(this.validate("+5534940028922"))
        }
    }

    @Test
    internal fun `deve retornar false para telefone formato invalido`() {
        with(KeyType.PHONE) {
            assertFalse(this.validate("telefone invalido"))
            assertFalse(this.validate("34940028922"))
            assertFalse(this.validate("+5534940028922222"))
        }
    }

    @Test
    internal fun `deve retornar false para telefone nulo ou vazio`() {
        with(KeyType.PHONE) {
            assertFalse(this.validate(""))
            assertFalse(this.validate(null))
        }
    }

    @Test
    internal fun `deve retornar true para email valido`() {
        with(KeyType.EMAIL) {
            assertTrue(this.validate("test@email.com"))
            assertTrue(this.validate("test@email.com.br"))
            assertTrue(this.validate("test@email.br"))
        }
    }

    @Test
    internal fun `deve retornar false para email invalido`() {
        with(KeyType.EMAIL) {
            assertFalse(this.validate("email invalido"))
            assertFalse(this.validate("test@"))
            assertFalse(this.validate("test@email"))
        }
    }

    @Test
    internal fun `deve retornar false para email nulo ou vazio`() {
        with(KeyType.EMAIL) {
            assertFalse(this.validate(""))
            assertFalse(this.validate(null))
        }
    }

    @Test
    internal fun `deve retornar true para chave aleatoria valido`() {
        with(KeyType.RANDOM) {
            assertTrue(this.validate(""))
            assertTrue(this.validate(null))
        }
    }

    @Test
    internal fun `deve retornar false para chave aleatorria invalido`() {
        with(KeyType.RANDOM) {
            assertFalse(this.validate("chave aleatorai invalida"))
        }
    }

    @Test
    internal fun `deve retornar uma NovaChavePixRequestGRpc`() {
        val newPixKeyRequest = NewPixKeyRequest(
            keyType = KeyType.RANDOM,
            keyValue = "",
            accountType = AccountType.CONTA_POUPANCA
        )

        val novaChavePixRequestGRpc = newPixKeyRequest.toModel(UUID.randomUUID())

        assertEquals(newPixKeyRequest.keyValue, novaChavePixRequestGRpc.chave)
        assertEquals(newPixKeyRequest.keyType?.gRpcAttr, novaChavePixRequestGRpc.tipoDaChave)
        assertEquals(newPixKeyRequest.accountType?.gRpcAttr, novaChavePixRequestGRpc.tipoDaConta)
    }
}