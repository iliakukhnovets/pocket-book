package com.example.pocketbook.presentation.screen.my_books.info.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class MyBooksInfoAdapter(
    private val context: Context?,
    private val listener: ItemListener<BookResponse>,
    private val listOfItems: List<BookResponse>
) : RecyclerView.Adapter<MyBooksInfoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBooksInfoHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.book_info_screen_item, parent, false)
        return MyBooksInfoHolder(
            view,
            listener,
            listOfItems
        )
    }

    override fun onBindViewHolder(holder: MyBooksInfoHolder, position: Int) {
        val model = listOfItems[position]
        holder.bindItem()
        when {
            model.isBookFinished == "" -> holder.binding.myBookInfoStatus.visibility = View.GONE
            model.isBookFinished != " " -> holder.binding.myBookInfoStatus.text =
                model.isBookFinished
        }
        holder.binding.myBookInfoStatus.text = model.isBookFinished
        holder.binding.myBookInfoBookName.text = model.bookName
        holder.binding.myBookInfoBookAuthor.text = model.bookAuthor
        holder.binding.myBooksBookInfoRatingBtn.text = model.bookRating.toString()
        holder.binding.myBooksBookInfoSubscribeBtn.text = model.typeOfBookSubscribe
        if (context != null) {
            Glide.with(context).load(model.imageUrl).into(holder.binding.myBookInfoItemImage)
        }
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}