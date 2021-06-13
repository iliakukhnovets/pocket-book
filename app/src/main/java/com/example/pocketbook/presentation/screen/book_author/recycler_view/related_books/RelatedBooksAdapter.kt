package com.example.pocketbook.presentation.screen.book_author.recycler_view.related_books

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class RelatedBooksAdapter(
    private val context: Context?,
    private val listOfBooks: List<BookResponse>,
    private val itemListener: ItemListener<BookResponse>
) : RecyclerView.Adapter<RelatedBooksHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedBooksHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.selected_book_screen_related_books_item, parent, false)
        return RelatedBooksHolder(view, listOfBooks, itemListener)
    }

    override fun onBindViewHolder(holder: RelatedBooksHolder, position: Int) {
        val model = listOfBooks[position]
        holder.bindItemListener()
        if (context != null) {
            Glide.with(context)
                .load(model.imageUrl)
                .transform(
                    RoundedCornersTransformation(
                        10,
                        10,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
                .into(holder.binding.relatedBookImage)
        }
        holder.binding.relatedBookRating.numStars = model.bookRating
        holder.binding.relatedBookName.text = model.bookName
        holder.binding.relatedBookAuthor.text = model.bookAuthor
    }

    override fun getItemCount(): Int {
        return listOfBooks.size
    }
}