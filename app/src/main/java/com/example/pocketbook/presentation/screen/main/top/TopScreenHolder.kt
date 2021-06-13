package com.example.pocketbook.presentation.screen.main.top

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.network.response.ImageCollectionResponse
import com.example.pocketbook.databinding.MainScreenTopCardViewItemBinding

class TopScreenHolder(
    itemView: View,
    private val itemClicked: ItemListener<ImageCollectionResponse>,
    private val listOfItems: List<ImageCollectionResponse>
) : RecyclerView.ViewHolder(itemView) {
    val binding = MainScreenTopCardViewItemBinding.bind(itemView)

    fun bindClickListener() {
        itemView.setOnClickListener {
            itemClicked.itemClicked(
                listOfItems[layoutPosition]
            )
        }
    }
}