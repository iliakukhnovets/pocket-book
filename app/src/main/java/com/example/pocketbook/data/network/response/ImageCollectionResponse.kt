package com.example.pocketbook.data.network.response

import com.google.gson.annotations.SerializedName

data class ImageCollectionResponse(
    @SerializedName("back_image_url")
    var backGroundImageUrl: String,

    @SerializedName("button_text")
    val buttonText: String,

    @SerializedName("header_text")
    val headerText: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("image_url")
    var imageUrl: String,

    @SerializedName("tab_text")
    val tabText: String,

    @SerializedName("image_text")
    val text: String
)