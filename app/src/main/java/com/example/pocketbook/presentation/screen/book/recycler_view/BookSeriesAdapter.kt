package com.example.pocketbook.presentation.screen.book.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class BookSeriesAdapter(
    private val context: Context?,
    private val listOfBooks: List<BookResponse>,
    private val itemListener: ItemListener<BookResponse>
) : RecyclerView.Adapter<BookSeriesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSeriesHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.selected_book_screen_book_series_item, parent, false)
        return com.example.pocketbook.presentation.screen.book.recycler_view.BookSeriesHolder(
            view,
            listOfBooks,
            itemListener
        )
    }

    override fun onBindViewHolder(holder: BookSeriesHolder, position: Int) {
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
                .into(holder.binding.relatedBookSeriesImage)
        }
        holder.binding.relatedBookSeriesTag.text = model.seriesOrderTag
        if (holder.binding.relatedBookSeriesTag.text.isNotEmpty()) {
            holder.binding.relatedBookSeriesTag.visibility = View.VISIBLE
        }
        holder.binding.relatedBookSeriesRating.numStars = model.bookRating
        holder.binding.relatedBookSeriesName.text = model.bookName
        holder.binding.relatedBookSeriesAuthor.text = model.bookAuthor
    }

    override fun getItemCount(): Int {
        return listOfBooks.size
    }
}