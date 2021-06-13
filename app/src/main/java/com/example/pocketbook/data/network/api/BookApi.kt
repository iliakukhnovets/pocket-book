package com.example.pocketbook.data.network.api

import com.example.pocketbook.data.classes.my_books.BooksStatusDataClass
import com.example.pocketbook.data.classes.search.BookCategoriesDataClass
import com.example.pocketbook.data.classes.search.BooksStylesDataClass
import com.example.pocketbook.data.network.response.BookResponse
import retrofit2.Call
import retrofit2.http.*

interface BookApi {
    @GET("data/Book")
    fun getBooks(): Call<List<BookResponse>>

    @GET("data/Book")
    fun getRelatedBooks(@Query("where") bookStyle: String): Call<List<BookResponse>>

    @GET("data/Book")
    fun getBookAuthorSeries(
        @Query("where") bookSeriesTag: String,
        @Query("sortBy") sortBy: String
    ): Call<List<BookResponse>>

    @GET("data/Book")
    fun getRelatedBookAuthorBooks(@Query("where") authorName: String): Call<List<BookResponse>>

    @GET("data/Book/count")
    fun getBooksCount(): Call<String>

    @GET("data/Book")
    fun getBooksCountProperty(
        @Query("where") property: String,
        @Query("property") column: String
    ): Call<String>

    @GET("data/Book")
    fun getAllBooks(
        @Query("pageSize") pageSize: Int,
        @Query("offset") offSet: Int,
        @Query("where") type: String,
        @Query("sortBy") sortingOrder: String,
    ): Call<List<BookResponse>>

    @GET("data/Book")
    fun getBookStyles(
        @Query("property") column: String,
        @Query("property") countColumn: String,
        @Query("groupBy") groupBy: String
    ): Call<List<BooksStylesDataClass>>

    @GET("data/Book")
    fun getBookCategories(
        @Query("where") style: String,
        @Query("property") column: String,
        @Query("property") countColumn: String,
        @Query("groupBy") groupBy: String
    ): Call<List<BookCategoriesDataClass>>

    @GET("data/Book")
    fun getBooksStatus(
        @Query("property") column: String,
        @Query("property") countColumn: String,
        @Query("groupBy") groupBy: String
    ): Call<List<BooksStatusDataClass>>

    @PUT("data/Book/{objectId}")
    fun updateBookRating(
        @Path("objectId") objectId: String,
    ): Call<BookResponse>
}
