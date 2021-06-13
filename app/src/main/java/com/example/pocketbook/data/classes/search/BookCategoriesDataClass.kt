package com.example.pocketbook.data.classes.search

import com.google.gson.annotations.SerializedName

data class BookCategoriesDataClass(
    @SerializedName("category")
    val categoryTag: String,
    @SerializedName("count")
    val categoryCount: String
)
