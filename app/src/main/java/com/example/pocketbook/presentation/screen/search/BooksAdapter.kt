package com.example.pocketbook.presentation.screen.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.data.classes.search.BooksDataClass
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class BooksAdapter(
    private val context: Context,
    private val listOfItems: List<BooksDataClass>,
    //private val itemListener: ItemListener<BooksDataClass>
    ) : RecyclerView.Adapter<BooksHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.search_screen_books_item, parent, false)
            return com.example.pocketbook.presentation.screen.search.BooksHolder(view, listOfItems)
        }

        override fun onBindViewHolder(holder: BooksHolder, position: Int) {
            val model = listOfItems[position]
          //  holder.bindItem()
            holder.binding.searchScreenBooksItemName.text = model.category
            holder.binding.searchScreenBooksItemCount.text = model.booksCount.toString()
            holder.binding.searchScreenBooksItemImage.setImageDrawable(model.booksImage)
        }

        override fun getItemCount(): Int {
            return listOfItems.size
        }
    }

