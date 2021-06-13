package com.example.pocketbook.presentation.screen.book.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.databinding.SelectedBookScreenBookSeriesItemBinding
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class BookSeriesHolder(
    itemView: View,
    private val list: List<BookResponse>,
    private val itemListener: ItemListener<BookResponse>
) : RecyclerView.ViewHolder(itemView) {
    val binding = SelectedBookScreenBookSeriesItemBinding.bind(itemView)

    fun bindItemListener() {
        itemView.setOnClickListener {
            itemListener.itemClicked(list[layoutPosition])
        }
    }
}
