package com.example.pocketbook.presentation.screen.my_books.top

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.databinding.MyBooksScreenImageItemBinding
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class MyBooksTopListHolder(
    itemView: View,
    private val listener: ItemListener<BookResponse>,
    private val listOfItems: List<BookResponse>
) : RecyclerView.ViewHolder(itemView) {
    val binding = MyBooksScreenImageItemBinding.bind(itemView)

    fun bindItemListener() {
        itemView.setOnClickListener {
            listener.itemClicked(listOfItems[layoutPosition])
        }
    }
}