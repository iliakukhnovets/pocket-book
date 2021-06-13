package com.example.pocketbook.data.network.api_service.book

import android.content.Context
import android.widget.Toast
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.databinding.MyBooksScreenBinding
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookNetworkService {

    fun loadAllUserBooks(context: Context?, binding: MyBooksScreenBinding) {
        NetworkClient.buildBookApiClient().getBooksCount().enqueue(
            object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        getCount(response, binding)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    if (context != null) {
                        showToast(context, DATA_FAIL)
                    }
                }
            }
        )
    }

    private fun getCount(response: Response<String>, binding: MyBooksScreenBinding) {
        val builder = StringBuilder()
        val count = response.body()
        builder.append("Все ").append(count)
        binding.myBooksAllUserBooks.text = builder.toString()
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}