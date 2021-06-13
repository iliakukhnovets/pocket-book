package com.example.pocketbook.presentation.screen.my_books.books_status

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.data.classes.my_books.BooksStatusDataClass
import com.example.pocketbook.presentation.screen.my_books.BookMenuListener

class BooksStatusAdapter(
    private val context: Context?,
    private val menuListener: BookMenuListener<BooksStatusDataClass>,
    private val listOfMenu: List<BooksStatusDataClass>
) : RecyclerView.Adapter<BooksStatusHolder>() {

    private val read = "Прочитал"
    private val wantToRead = "Хочу прочитать"
    private val readYet = "Читаю"
    private val unsorted = "Несортированное"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksStatusHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.my_books_screen_status_item, parent, false)
        return com.example.pocketbook.presentation.screen.my_books.books_status.BooksStatusHolder(
            view,
            menuListener,
            listOfMenu
        )
    }

    override fun onBindViewHolder(holder: BooksStatusHolder, position: Int) {
        val model = listOfMenu[position]
        holder.bindItem()
        holder.binding.myBooksWantReadCount.text = model.booksStatusCount.toString()
        holder.binding.myBooksWantToRead.text = model.statusText
        when (model.statusText) {
            read -> setImage(holder, R.drawable.ic_my_books_finish_read_24)
            wantToRead -> setImage(holder, R.drawable.ic_my_books_want_read_24)
            readYet -> setImage(holder, R.drawable.ic_my_books_read_24)
            unsorted -> setImage(holder, R.drawable.ic_my_books_unsorted_24)
        }
    }

    private fun setImage(holder: BooksStatusHolder, image: Int) {
        holder.binding.myBooksWantToRead.setCompoundDrawablesWithIntrinsicBounds(
            image,
            0,
            0,
            0
        )
    }

    override fun getItemCount(): Int {
        return listOfMenu.size
    }
}