package com.example.pocketbook.data.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
     val userId: String,
    @SerializedName("name")
     val userName: String,
    @SerializedName("second_name")
     val userSecondName: String,
    @SerializedName("subscribers")
     val userSubscribe: String,
)