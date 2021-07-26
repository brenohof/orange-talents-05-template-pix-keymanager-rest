package br.com.zup.shared

import br.com.zup.ListaChavePixResponse
import br.com.zup.ListaTodasChavePixResponse
import br.com.zup.TipoDaChave
import br.com.zup.TipoDaConta
import br.com.zup.create.AccountType
import br.com.zup.create.KeyType
import br.com.zup.detail.AccountResponse
import br.com.zup.detail.DetailPixKeyResponse
import br.com.zup.detail.OwnerResponse
import br.com.zup.list.ClientPixKeyResponse
import java.time.LocalDateTime
import java.time.ZoneOffset

fun ListaChavePixResponse.toModel(): DetailPixKeyResponse {
    return DetailPixKeyResponse(
        pixId = pixId,
        clientId = clienteId,
        keyType = when (tipoDaChave) {
            TipoDaChave.CPF -> KeyType.CPF
            TipoDaChave.EMAIL -> KeyType.EMAIL
            TipoDaChave.CHAVE_ALEATORIA -> KeyType.RANDOM
            TipoDaChave.TELEFONE_CELULAR -> KeyType.PHONE
            else -> null
        },
        keyValue = chave,
        owner = OwnerResponse(name = titular.nome, cpf = titular.cpf),
        account = AccountResponse(
            institutionName = conta.nomeDaInstitucao,
            branch = conta.agencia,
            accountNumber = conta.numero,
            accountType = when (conta.tipo) {
                TipoDaConta.CONTA_POUPANCA -> AccountType.CONTA_POUPANCA
                TipoDaConta.CONTA_CORRENTE -> AccountType.CONTA_CORRENTE
                else -> null
            }
        ),
        createdAt = LocalDateTime.ofEpochSecond(criadaEm.seconds, criadaEm.nanos, ZoneOffset.UTC)
    )
}

fun ListaTodasChavePixResponse.toModel(): List<ClientPixKeyResponse> {
    return chavesList.map {
        ClientPixKeyResponse(
            pixId = it.pixId,
            clientId = it.clienteId,
            keyType = when (it.tipoDaChave) {
                TipoDaChave.CPF -> KeyType.CPF
                TipoDaChave.EMAIL -> KeyType.EMAIL
                TipoDaChave.CHAVE_ALEATORIA -> KeyType.RANDOM
                TipoDaChave.TELEFONE_CELULAR -> KeyType.PHONE
                else -> null
            },
            keyValue = it.chave,
            accountType = when (it.tipoDaConta) {
                TipoDaConta.CONTA_POUPANCA -> AccountType.CONTA_POUPANCA
                TipoDaConta.CONTA_CORRENTE -> AccountType.CONTA_CORRENTE
                else -> null
            },
            createdAt = LocalDateTime.ofEpochSecond(it.criadaEm.seconds, it.criadaEm.nanos, ZoneOffset.UTC)
        )
    }
}