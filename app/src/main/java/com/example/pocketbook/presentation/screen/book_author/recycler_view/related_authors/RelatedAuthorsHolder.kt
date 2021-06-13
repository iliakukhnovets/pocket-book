package com.example.pocketbook.presentation.screen.book_author.recycler_view.related_authors

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.network.response.BookAuthorResponse
import com.example.pocketbook.databinding.BookAuthorScreenRelatedAuthorsItemBinding
import com.example.pocketbook.presentation.screen.main.top.ImageListener

class RelatedAuthorsHolder(
    itemView: View,
    private val itemListener: ImageListener,
    private val listOfAuthors: List<BookAuthorResponse>
) : RecyclerView.ViewHolder(itemView) {
    val binding = BookAuthorScreenRelatedAuthorsItemBinding.bind(itemView)

    fun bindImage() {
        itemView.setOnClickListener(View.OnClickListener {
            itemListener.imageClicked(listOfAuthors[adapterPosition])
        })
    }
}