package br.com.zup.detail

import br.com.zup.ListaChavePixResponse
import br.com.zup.TipoDaChave
import br.com.zup.TipoDaConta
import br.com.zup.create.AccountType
import br.com.zup.create.KeyType
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