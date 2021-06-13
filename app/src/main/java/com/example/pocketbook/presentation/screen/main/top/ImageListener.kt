package com.example.pocketbook.presentation.screen.main.top

import com.example.pocketbook.data.network.response.BookAuthorResponse

interface ImageListener {
    fun imageClicked(response: BookAuthorResponse)
}