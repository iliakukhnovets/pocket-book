package com.example.pocketbook.data

import com.example.pocketbook.BuildConfig
import com.example.pocketbook.data.network.api.BookApi
import com.example.pocketbook.data.network.api.BookAuthorApi
import com.example.pocketbook.data.network.api.ImageCollectionApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    lateinit var instance: NetworkClient
    private lateinit var bookApi: BookApi
    private lateinit var bookAuthorApi: BookAuthorApi
    private lateinit var imageCollectionApi: ImageCollectionApi

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor { message ->
            println("LOG-APP: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }

    private var retrofit: Retrofit =
        Retrofit.Builder()
            .client(client.build())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()


    fun buildBookApiClient(): BookApi {
        bookApi = retrofit.create(BookApi::class.java)
        return bookApi
    }

    fun buildBookAuthorApi(): BookAuthorApi {
        bookAuthorApi = retrofit.create(BookAuthorApi::class.java)
        return bookAuthorApi
    }

    fun buildImageCollectionClient(): ImageCollectionApi {
        imageCollectionApi = retrofit.create(ImageCollectionApi::class.java)
        return imageCollectionApi
    }
}
