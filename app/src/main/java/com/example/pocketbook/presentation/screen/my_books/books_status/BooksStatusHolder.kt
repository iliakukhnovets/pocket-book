package com.example.pocketbook.presentation.screen.my_books.books_status

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.classes.my_books.BooksStatusDataClass
import com.example.pocketbook.databinding.MyBooksScreenStatusItemBinding
import com.example.pocketbook.presentation.screen.my_books.BookMenuListener

class BooksStatusHolder(
    itemView: View,
    private val itemListener: BookMenuListener<BooksStatusDataClass>,
    private val listOfItems: List<BooksStatusDataClass>
) : RecyclerView.ViewHolder(itemView) {
    val binding = MyBooksScreenStatusItemBinding.bind(itemView)

    fun bindItem() {
        itemView.setOnClickListener {
            itemListener.menuClicked(listOfItems[adapterPosition])
        }
    }
}