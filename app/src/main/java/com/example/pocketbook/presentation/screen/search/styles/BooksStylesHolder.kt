package com.example.pocketbook.presentation.screen.search.styles

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.classes.search.BookCategoriesDataClass
import com.example.pocketbook.databinding.BookCategoriesScreenItemBinding
import com.example.pocketbook.databinding.SearchScreenStylesMenuItemBinding
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class BooksStylesHolder(
    itemView: View,
    private val itemListener: ItemListener<BookCategoriesDataClass>,
    private val listOfItems: List<BookCategoriesDataClass>
) : RecyclerView.ViewHolder(itemView) {
    val binding = BookCategoriesScreenItemBinding.bind(itemView)

    fun bindItem() {
        itemView.setOnClickListener {
            itemListener.itemClicked(listOfItems[adapterPosition])
        }
    }
}