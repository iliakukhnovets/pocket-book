package com.example.pocketbook.presentation.screen.my_books.top

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class MyBooksTopListAdapter(
    private val context: Context?,
    private val listener: ItemListener<BookResponse>,
    private val listOfItems: List<BookResponse>
) : RecyclerView.Adapter<MyBooksTopListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBooksTopListHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.my_books_screen_image_item, parent, false)
        return MyBooksTopListHolder(view, listener, listOfItems)
    }

    override fun onBindViewHolder(holderTop: MyBooksTopListHolder, position: Int) {
        val model = listOfItems[position]
        holderTop.bindItemListener()
        if (context != null) {
            Glide.with(context)
                .load(model.imageUrl)
                .transform(RoundedCornersTransformation(10, 10))
                .into(holderTop.binding.myBooksTopRecyclerViewImage)
        }
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}