package com.example.pocketbook.presentation.screen.book_author.recycler_view.related_books

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.databinding.SelectedBookScreenRelatedBooksItemBinding
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class RelatedBooksHolder(
    itemView: View,
    private val list: List<BookResponse>,
    private val itemListener: ItemListener<BookResponse>
) : RecyclerView.ViewHolder(itemView) {
    val binding = SelectedBookScreenRelatedBooksItemBinding.bind(itemView)

    fun bindItemListener() {
        itemView.setOnClickListener(View.OnClickListener {
            itemListener.itemClicked(list[layoutPosition])
        })
    }
}