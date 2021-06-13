package com.example.pocketbook.presentation.screen.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.classes.search.BooksStylesDataClass
import com.example.pocketbook.databinding.SearchScreenStylesMenuItemBinding
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class SearchBookStylesHolder(
    itemView: View,
    private val listOfItems: List<BooksStylesDataClass>,
    private val itemListener: ItemListener<BooksStylesDataClass>
) :
    RecyclerView.ViewHolder(itemView) {
    val binding = SearchScreenStylesMenuItemBinding.bind(itemView)

    fun bindItem() {
        itemView.setOnClickListener {
            itemListener.itemClicked(listOfItems[layoutPosition])
        }
    }
}
