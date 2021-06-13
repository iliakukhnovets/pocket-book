package com.example.pocketbook.data.network.api

import com.example.pocketbook.data.network.response.BookAuthorResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookAuthorApi {
    @GET("data/BookAuthor")
    fun getRelatedBookAuthor(@Query("where") bookStyle: String): Call<List<BookAuthorResponse>>

    @GET("data/BookAuthor")
    fun getBookAuthor(@Query("where") author: String): Call<List<BookAuthorResponse>>
}