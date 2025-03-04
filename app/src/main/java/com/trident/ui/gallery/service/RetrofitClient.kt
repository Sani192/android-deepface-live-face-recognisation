package com.trident.ui.gallery.service

import com.trident.ui.gallery.models.MoveImagesRequest
import com.trident.ui.gallery.models.MoveImagesResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    public const val BASE_URL = "http://192.168.29.170:5000/"

    val imagesApiService: ImagesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImagesApiService::class.java)
    }
}