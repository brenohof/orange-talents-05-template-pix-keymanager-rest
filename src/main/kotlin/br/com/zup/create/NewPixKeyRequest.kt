package br.com.zup.create

import br.com.zup.NovaChavePixRequestGRpc
import br.com.zup.TipoDaChave
import br.com.zup.TipoDaConta
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
data class NewPixKeyRequest(
    @field:NotBlank
    val clientId: String,

    @field:NotNull
    val keyType: KeyType,

    @field:NotNull
    @field:Size(max = 77)
    val keyValue: String,

    @field:NotNull
    val accountType: AccountType
) {
    fun toModel(): NovaChavePixRequestGRpc {
        return NovaChavePixRequestGRpc.newBuilder()
            .setClienteId(clientId)
            .setTipoDaChave(TipoDaChave.valueOf(keyType.name))
            .setChave(keyValue)
            .setTipoDaConta(TipoDaConta.valueOf(accountType.name))
            .build()
    }
}
