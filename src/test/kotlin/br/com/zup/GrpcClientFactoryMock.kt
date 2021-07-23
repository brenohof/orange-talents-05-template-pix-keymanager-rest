package br.com.zup

import br.com.zup.shared.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import org.mockito.Mockito
import javax.inject.Singleton

@Factory
@Replaces(factory = GrpcClientFactory::class)
class MockitoStubFactory {

    @Singleton
    fun createPixKeyStubMock(): PixKeyManagerGrpcServiceGrpc.PixKeyManagerGrpcServiceBlockingStub {
        return Mockito.mock(PixKeyManagerGrpcServiceGrpc.PixKeyManagerGrpcServiceBlockingStub::class.java)
    }

    @Singleton
    fun deletePixKeyStubMock(): RemovePixKeyGrpcServiceGrpc.RemovePixKeyGrpcServiceBlockingStub? {
        return Mockito.mock(RemovePixKeyGrpcServiceGrpc.RemovePixKeyGrpcServiceBlockingStub::class.java)
    }
}