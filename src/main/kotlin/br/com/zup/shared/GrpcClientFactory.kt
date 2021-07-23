package br.com.zup.shared

import br.com.zup.PixKeyManagerGrpcServiceGrpc
import br.com.zup.RemovePixKeyGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun createPixKey() = PixKeyManagerGrpcServiceGrpc.newBlockingStub(channel)
    @Singleton
    fun deletePixKey() = RemovePixKeyGrpcServiceGrpc.newBlockingStub(channel)
}