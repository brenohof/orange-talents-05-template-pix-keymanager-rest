package br.com.zup.detail

import br.com.zup.create.AccountType
import br.com.zup.create.KeyType
import java.time.LocalDateTime

data class DetailPixKeyResponse(
    val pixId: String,
    val clientId: String,
    val keyType: KeyType?,
    val keyValue: String,
    val owner: OwnerResponse,
    val account: AccountResponse,
    val createdAt: LocalDateTime
)

data class OwnerResponse(val name: String, val cpf: String)
data class AccountResponse(
    val institutionName: String,
    val branch: String,
    val accountNumber: String,
    val accountType: AccountType?
)
