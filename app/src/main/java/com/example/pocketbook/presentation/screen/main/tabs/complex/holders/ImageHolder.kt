package com.example.pocketbook.presentation.screen.main.tabs.complex.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.databinding.MainScreenBooksTabImageItemBinding
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class ImageHolder(
    itemView: View,
    private val itemListener: ItemListener<Any>,
    private val listOfImages: List<Any>
) : RecyclerView.ViewHolder(itemView) {
    val binding = MainScreenBooksTabImageItemBinding.bind(itemView)

    fun bindClickedImage() {
        itemView.setOnClickListener {
            itemListener.itemClicked(listOfImages[adapterPosition])
        }
    }
}