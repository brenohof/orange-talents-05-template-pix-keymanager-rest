package br.com.zup.create

import br.com.zup.NovaChavePixRequestGRpc
import br.com.zup.TipoDaChave
import br.com.zup.TipoDaConta
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class NewPixKeyRequest(
    @field:NotNull
    val keyType: KeyType?,

    @field:Size(max = 77)
    val keyValue: String?,

    @field:NotNull
    val accountType: AccountType?
) {
    fun toModel(clientId: UUID): NovaChavePixRequestGRpc {
        return NovaChavePixRequestGRpc.newBuilder()
            .setClienteId(clientId.toString())
            .setTipoDaChave(keyType?.gRpcAttr ?: TipoDaChave.UNKNOWN_TIPO_CHAVE)
            .setChave(keyValue ?: "")
            .setTipoDaConta(accountType?.gRpcAttr ?: TipoDaConta.UNKNOWN_TIPO_CONTA)
            .build()
    }
}

enum class KeyType(val gRpcAttr: TipoDaChave) {
    CPF(TipoDaChave.CPF) {
        override fun validate(keyValue: String?): Boolean {
            if (keyValue.isNullOrBlank()) return false

            if (!keyValue.matches("^[0-9]{11}\$".toRegex())) return false

            return CPFValidator().run {
                initialize(null)
                isValid(keyValue, null)
            }
        }
    }, PHONE(TipoDaChave.TELEFONE_CELULAR) {
        override fun validate(keyValue: String?): Boolean {
            if (keyValue.isNullOrBlank()) return false

            return keyValue.matches("^\\+[1-9][0-9][1-9]{2}9[1-9][0-9]{7}\$".toRegex())
        }
    },
    EMAIL(TipoDaChave.EMAIL) {
        override fun validate(keyValue: String?): Boolean {
            if (keyValue.isNullOrBlank()) return false

            return EmailValidator().run {
                initialize(null)
                isValid(keyValue, null)
            }
        }
    }, RANDOM(TipoDaChave.CHAVE_ALEATORIA) {
        override fun validate(keyValue: String?) = keyValue.isNullOrEmpty()
    };

    abstract fun validate(keyValue: String?): Boolean
}

enum class AccountType(val gRpcAttr: TipoDaConta) {
    CONTA_CORRENTE(TipoDaConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoDaConta.CONTA_POUPANCA);
}
