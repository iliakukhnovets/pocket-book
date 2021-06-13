package com.example.pocketbook.presentation.screen.main.top

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.data.network.response.ImageCollectionResponse
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class TopScreenAdapter(
    private val context: Context?,
    private val itemClicked: ItemListener<ImageCollectionResponse>,
    private val listOfItems: List<ImageCollectionResponse>
) : RecyclerView.Adapter<TopScreenHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopScreenHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.main_screen_top_card_view_item, parent, false)
        return com.example.pocketbook.presentation.screen.main.top.TopScreenHolder(
            view,
            itemClicked,
            listOfItems
        )
    }

    override fun onBindViewHolder(holder: TopScreenHolder, position: Int) {
        val model = listOfItems[position]
        holder.bindClickListener()
        holder.binding.mainFragmentTopRecyclerViewText.text = model.headerText
        if (context != null) {
            Glide.with(context).load(model.imageUrl)
                .transform(RoundedCornersTransformation(10, 0)).into(
                    holder.binding.mainFragmentTopRecyclerViewImage
                )
        }
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}