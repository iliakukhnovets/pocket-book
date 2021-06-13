package com.example.pocketbook.presentation.screen.main.bottom

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.data.classes.main.bottom.MenuDataClass
import com.example.pocketbook.databinding.MainScreenBottomItemBinding
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class BottomBarHolder(
    itemView: View,
    private val list: List<MenuDataClass>,
    private val itemListener: ItemListener<MenuDataClass>
) : RecyclerView.ViewHolder(itemView) {
    val binding = MainScreenBottomItemBinding.bind(itemView)

    fun binMenuItem() {
        itemView.setOnClickListener {
            itemListener.itemClicked(list[adapterPosition])
        }
    }
}