package com.ddevs.edgeify.network

import com.ddevs.edgeify.models.ImageLinks
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("process")
    suspend fun processImage(@Part img: MultipartBody.Part): Response<ImageLinks>
}