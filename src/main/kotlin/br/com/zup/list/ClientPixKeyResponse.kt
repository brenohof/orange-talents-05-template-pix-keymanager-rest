package br.com.zup.list

import br.com.zup.create.AccountType
import br.com.zup.create.KeyType
import java.time.LocalDateTime

data class ClientPixKeyResponse(
    val pixId: String,
    val clientId: String,
    val keyType: KeyType?,
    val keyValue: String,
    val accountType: AccountType?,
    val createdAt: LocalDateTime,
)