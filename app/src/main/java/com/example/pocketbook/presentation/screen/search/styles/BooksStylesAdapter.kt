package com.example.pocketbook.presentation.screen.search.styles

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.data.classes.search.BookCategoriesDataClass
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class BooksStylesAdapter(
    private val context: Context,
    private val listOfItems: List<BookCategoriesDataClass>,
    private val itemListener: ItemListener<BookCategoriesDataClass>
) : RecyclerView.Adapter<BooksStylesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksStylesHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.book_categories_screen_item, parent, false)
        return BooksStylesHolder(view, itemListener, listOfItems)
    }

    override fun onBindViewHolder(stylesHolder: BooksStylesHolder, position: Int) {
        val model = listOfItems[position]
        stylesHolder.bindItem()
        stylesHolder.binding.searchScreenBooksCategoriesTag.text = model.categoryTag
        stylesHolder.binding.searchScreenBooksCategoriesCount.text = model.categoryCount
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}