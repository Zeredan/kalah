package com.example.networking_core.retrofit.di

import com.example.networking_core.retrofit.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitNetworkSettings{
    var networkingUrl: String = "qwer"
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkingRetrofitHiltModule {
    @Provides
    fun provideRetrofitService(retrofit: Retrofit) : RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        client: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideBaseUrl() : String {
        return RetrofitNetworkSettings.networkingUrl
    }

    @Provides
    fun provideGsonFactory() : Converter.Factory{
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(interceptors: Set<@JvmSuppressWildcards Interceptor>) : OkHttpClient{
        return OkHttpClient.Builder()
            .apply{
                interceptors.forEach {
                    addInterceptor(it)
                }
            }
            .build()
    }

    @Provides
    @IntoSet
    fun provideLoggingInterceptor() : Interceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}