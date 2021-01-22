package com.github.orelzion.spotifyclone.model

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitCreator {
    inline fun <reified T> createService(
        baseUrl: String,
        vararg interceptors: Interceptor = emptyArray()
    ): T {
        val client = OkHttpClient.Builder().apply {
            interceptors.forEach {
                addInterceptor(it)
            }
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()


        val retrofit = Retrofit.Builder()
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
                    .asConverterFactory(MediaType.get("application/json"))
            )
            .baseUrl(baseUrl)
            .client(client)
            .build()

        return retrofit.create(T::class.java)
    }
}