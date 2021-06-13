package com.example.pocketbook.presentation.screen.main.bottom

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.data.classes.main.bottom.MenuDataClass
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class BottomBarAdapter(
    val context: Context?,
    val list: List<MenuDataClass>,
    val itemListener: ItemListener<MenuDataClass>
) : RecyclerView.Adapter<BottomBarHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomBarHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.main_screen_bottom_item, parent, false)
        return BottomBarHolder(view, list, itemListener)
    }

    override fun onBindViewHolder(holder: BottomBarHolder, position: Int) {
        val model = list[position]
        holder.binMenuItem()
        holder.binding.mainBottomItemBtn.text = model.text
        holder.binding.mainBottomItemBtn.setCompoundDrawablesWithIntrinsicBounds(
            0,
            model.image,
            0,
            0
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }
}