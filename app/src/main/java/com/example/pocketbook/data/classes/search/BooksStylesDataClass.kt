package com.example.pocketbook.data.classes.search

import com.google.gson.annotations.SerializedName

data class BooksStylesDataClass(
    @SerializedName("style")
    val style:String,
    @SerializedName("count")
    val categoryCount:String,
    @SerializedName("style_image_url")
    val categoryImageUrl:String
)
