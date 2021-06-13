package com.example.pocketbook.data.network.api

import com.example.pocketbook.data.network.response.ImageCollectionResponse
import retrofit2.Call
import retrofit2.http.GET

interface ImageCollectionApi {
    @GET("data/ImageCollections")
    fun getUrls(): Call<List<ImageCollectionResponse>>
}