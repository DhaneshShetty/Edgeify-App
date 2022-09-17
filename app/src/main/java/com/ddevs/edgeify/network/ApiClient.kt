package com.ddevs.edgeify.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val loggingInterceptor = HttpLoggingInterceptor()
    init{
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(10,TimeUnit.MINUTES)
        .callTimeout(10,TimeUnit.MINUTES)
        .connectTimeout(10,TimeUnit.MINUTES)
        .build()
    }
    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("http:/3.83.130.58:5000/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    val service: ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}