package com.example.pocketbook.data.classes.search

import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName

data class BooksDataClass(
    @SerializedName("count")
    val booksCount: Int,
    val booksImage: Drawable?,
    @SerializedName("type")
    val category: String
)
