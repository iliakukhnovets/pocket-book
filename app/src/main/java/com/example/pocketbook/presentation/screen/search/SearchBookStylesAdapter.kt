package com.example.pocketbook.presentation.screen.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.data.classes.search.BooksStylesDataClass
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class SearchBookStylesAdapter(
    private val context: Context,
    private val listOfItems: List<BooksStylesDataClass>,
    private val itemListener: ItemListener<BooksStylesDataClass>
) : RecyclerView.Adapter<SearchBookStylesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBookStylesHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.search_screen_styles_menu_item, parent, false)
        return SearchBookStylesHolder(view, listOfItems, itemListener)
    }

    override fun onBindViewHolder(holder: SearchBookStylesHolder, position: Int) {
        val model = listOfItems[position]
        holder.bindItem()
        holder.binding.searchScreenBooksItemTag.text = model.style
        holder.binding.searchScreenBooksCount.text = model.categoryCount
        Glide.with(context).load(R.mipmap.books_small).transform(
            RoundedCornersTransformation(
                10,
                10,
                RoundedCornersTransformation.CornerType.ALL
            )
        )
            .into(holder.binding.searchScreenBooksItemImage)
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}