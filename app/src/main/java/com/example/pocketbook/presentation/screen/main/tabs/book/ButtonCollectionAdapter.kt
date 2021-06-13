package com.example.pocketbook.presentation.screen.main.tabs.book

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.data.classes.main.tabs.ButtonsHolderDataClass
import com.example.pocketbook.presentation.screen.main.tabs.complex.holders.ButtonsHolder

class ButtonCollectionAdapter(
    private val context: Context?,
    private val listOfButtons: List<ButtonsHolderDataClass>
) : RecyclerView.Adapter<ButtonsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonsHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.main_screen_books_tab_buttons_item, parent, false)
        return ButtonsHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonsHolder, position: Int) {
        val model = listOfButtons[position]
        holder.binding.mainFragmentButtonsRecyclerViewItem.text = model.text
        holder.binding.mainFragmentButtonsRecyclerViewItem.setCompoundDrawablesWithIntrinsicBounds(
            model.image,
            0,
            0,
            0
        )
    }

    override fun getItemCount(): Int {
        return listOfButtons.size
    }
}