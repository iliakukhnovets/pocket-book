package com.example.pocketbook.presentation.screen.book_author.recycler_view.related_authors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.data.network.response.BookAuthorResponse
import com.example.pocketbook.presentation.screen.main.top.ImageListener

class RelatedAuthorsAdapter(
    private val context: Context?,
    private val listOfAuthors: List<BookAuthorResponse>,
    private val itemListener: ImageListener
) : RecyclerView.Adapter<RelatedAuthorsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedAuthorsHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.book_author_screen_related_authors_item, parent, false)
        return RelatedAuthorsHolder(view, itemListener, listOfAuthors)
    }
     //TODO переделать под количество книг
    override fun onBindViewHolder(holder: RelatedAuthorsHolder, position: Int) {
        val response:BookAuthorResponse = listOfAuthors[position]
        holder.bindImage()
        holder.binding.bookAuthorRelatedAuthorName.text = response.bookAuthor
        //holder.binding.bookAuthorRelatedAuthorBooksCount.text = model.bookAuthorStyle
        if (context != null) {
            Glide.with(context).load(response.bookAuthorImageUrl)
                .into(holder.binding.bookAuthorRelatedBookImage)
        }
    }

    override fun getItemCount(): Int {
        return listOfAuthors.size
    }
}