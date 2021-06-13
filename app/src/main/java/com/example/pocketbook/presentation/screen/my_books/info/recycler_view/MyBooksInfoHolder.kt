package com.example.pocketbook.presentation.screen.my_books.info.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.databinding.BookInfoScreenItemBinding
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class MyBooksInfoHolder(
    itemView: View,
    private val listener: ItemListener<BookResponse>,
    private val listOfItems: List<BookResponse>
) : RecyclerView.ViewHolder(itemView) {
    val binding = BookInfoScreenItemBinding.bind(itemView)

    fun bindItem() {
        binding.myBookInfoItemImage.clipToOutline = true
        itemView.setOnClickListener {
            listener.itemClicked(listOfItems[adapterPosition])
        }
    }
}