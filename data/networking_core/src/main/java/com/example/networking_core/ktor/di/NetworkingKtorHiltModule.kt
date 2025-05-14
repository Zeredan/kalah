package com.example.networking_core.ktor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object KtorNetworkSettings{
    var networkingUrl: String = ""
}

fun createHttpClient(engine: HttpClientEngine) : HttpClient {
    return HttpClient(engine) {

//        install(Logging){
//            level = LogLevel.ALL
//        }
//        install(ContentNegotiation){
//            json(
//                json = Json{
//                    ignoreUnknownKeys = true
//                }
//            )
//        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkingKtorHiltModule {
    @Provides
    fun provideHttpClient(engine: HttpClientEngine) : HttpClient{
        return createHttpClient(engine)
    }

    @Provides
    fun provideHttpClientEngine(interceptors: Set<@JvmSuppressWildcards Interceptor>) : HttpClientEngine {
        return OkHttp.create{
            interceptors.forEach {
                addInterceptor(it)
            }
        }
    }

    @Provides
    @IntoSet
    fun provideLoggingInterceptor() : Interceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}