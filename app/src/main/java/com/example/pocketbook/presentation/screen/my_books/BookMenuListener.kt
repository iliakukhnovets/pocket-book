package com.example.pocketbook.presentation.screen.my_books

interface BookMenuListener<T> {
    fun menuClicked(model: T)
}