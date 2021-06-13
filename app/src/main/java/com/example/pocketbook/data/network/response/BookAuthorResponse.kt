package com.example.pocketbook.data.network.response

import com.google.gson.annotations.SerializedName

data class BookAuthorResponse(
    @SerializedName("author")
    val bookAuthor: String,
    @SerializedName("biography")
    val authorBiography: String,
    @SerializedName("photo_url")
    val bookAuthorImageUrl: String,
    @SerializedName("style")
    val bookAuthorStyle: String
)