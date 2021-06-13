package com.example.pocketbook.data.classes.my_books

import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName

data class BooksStatusDataClass(
    val statusImage: Drawable?,
    @SerializedName("is_finished_flag")
    val statusText: String,
    @SerializedName("count")
    val booksStatusCount: Int
)
