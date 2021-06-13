package com.example.pocketbook.presentation.screen.main.tabs.complex.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.databinding.MainScreenBooksTabCollectionItemBinding
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class TodayRecommendationHolder(
    itemView: View,
    private val itemListener: ItemListener<Any>,
    private val listOfBooks: List<Any>
) : RecyclerView.ViewHolder(itemView) {
    val binding = MainScreenBooksTabCollectionItemBinding.bind(itemView)

    fun bindBooks() {
        itemView.setOnClickListener {
            itemListener.itemClicked(listOfBooks[adapterPosition])
        }
    }
}