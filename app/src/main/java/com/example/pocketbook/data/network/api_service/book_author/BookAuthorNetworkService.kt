package com.example.pocketbook.data.network.api_service.book_author

import android.content.Context
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.network.response.BookAuthorResponse
import com.example.pocketbook.databinding.BookAuthorScreenBinding
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.LOAD_ERROR
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookAuthorNetworkService {
    fun loadBookAuthorBooksCount(
        authorName: String,
        author: String,
        context: Context?,
        binding: BookAuthorScreenBinding,
    ) {
        NetworkClient.buildBookApiClient()
            .getBooksCountProperty("author='$authorName'", "Count($author)")
            .enqueue(
                object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful && response.body() != null) {
                            getBooksCount(response, binding)
                        } else if (response.errorBody() != null) {
                            if (context != null) {
                                showToast(context, DATA_FAIL)
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {

                    }

                }
            )
    }

    private fun getBooksCount(
        response: Response<String>,
        binding: BookAuthorScreenBinding,
    ) {
        val authorBooksCount = response.body().toString()
        val sb = StringBuilder()
        sb.append(authorBooksCount).append(" ").append("книги")
        binding.bookAuthorBooksNumber.text = sb.toString()
    }

    fun loadAuthorData(
        context: Context,
        authorName: String,
        binding: BookAuthorScreenBinding
    ) {
        NetworkClient
            .buildBookAuthorApi()
            .getBookAuthor("author='$authorName'")
            .enqueue(
                object : Callback<List<BookAuthorResponse>> {
                    override fun onResponse(
                        call: Call<List<BookAuthorResponse>>,
                        response: Response<List<BookAuthorResponse>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            getBookAuthor(response, binding, context)
                        } else if (response.errorBody() != null) {
                            showToast(context, DATA_FAIL)
                        }
                    }

                    override fun onFailure(call: Call<List<BookAuthorResponse>>, t: Throwable) {
                        showToast(context, LOAD_ERROR)
                    }
                }
            )
    }

    private fun getBookAuthor(
        response: Response<List<BookAuthorResponse>>,
        binding: BookAuthorScreenBinding,
        context: Context
    ) {
        val list = response.body()
        if (list != null) {
            binding.bookAuthorBiography.text = list[0].authorBiography
            context.let {
                Glide.with(it)
                    .load(list[0].bookAuthorImageUrl)
                    .into(binding.bookAuthorImage)
            }
        }
    }

//TODO добавить валидацию тостов

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}