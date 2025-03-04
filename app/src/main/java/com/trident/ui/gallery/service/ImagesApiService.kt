package com.trident.ui.gallery.service

import com.trident.ui.gallery.models.MoveImagesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApiService {

    @GET("list-directories")
    fun getImages(): Call<Map<String, List<String>>>

    @GET("move-images")
    fun moveImages(@Query("new_dir") directoryName: String, @Query("image_names") imageNames: List<String>): Call<MoveImagesResponse>
}