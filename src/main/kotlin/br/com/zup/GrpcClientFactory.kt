package br.com.zup

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {

    @Singleton
    fun pixKeyManagerClientStub(@GrpcChannel("pix-key") channel: ManagedChannel):
            PixKeyManagerGrpcServiceGrpc.PixKeyManagerGrpcServiceBlockingStub {
        return PixKeyManagerGrpcServiceGrpc.newBlockingStub(channel)
    }
}