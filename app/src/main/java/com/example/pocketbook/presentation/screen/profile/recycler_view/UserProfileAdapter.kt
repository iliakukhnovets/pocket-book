package com.example.pocketbook.presentation.screen.profile.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.data.classes.profile.UserProfileDataClass

class UserProfileAdapter(
    private val context: Context?,
    private val listOfMenuItems: MutableList<UserProfileDataClass>
) : RecyclerView.Adapter<UserProfileHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.profile_screen_item, parent, false)
        return UserProfileHolder(view)
    }

    override fun onBindViewHolder(holder: UserProfileHolder, position: Int) {
        val model = listOfMenuItems[position]
        holder.binding.profileRecyclerViewItemTag.text = model.bookTag
        holder.binding.profileRecyclerViewItemCount.text = model.booksData
        holder.binding.profileRecyclerViewItemImage.setImageDrawable(model.itemImage)
        holder.binding.profileRecyclerViewItemCountData.text = model.itemCount
    }

    override fun getItemCount(): Int {
        return listOfMenuItems.size
    }
}