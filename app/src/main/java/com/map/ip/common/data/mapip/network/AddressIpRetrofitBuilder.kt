package com.map.ip.common.data.mapip.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AddressIpRetrofitBuilder {

    private const val BASE_URL =
        "http://ip-api.com/"

    fun createApiService(): AddressIpService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(AddressIpService::class.java)
    }

    private fun getHttpClient(): OkHttpClient {
        val defaultLogging = HttpLoggingInterceptor()
        defaultLogging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        return httpClient
            .addInterceptor(createRequestInterceptor())
            .addInterceptor(defaultLogging)
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun createRequestInterceptor(): Interceptor = Interceptor { chain ->
        var request = chain.request()
        request = request.newBuilder().addHeader("Accept", "application/json").build()
        chain.proceed(request)
    }
}