package com.ddevs.edgeify.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://api.edgeify.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service by lazy{
        retrofit.create(ApiService::class.java)
    }
}