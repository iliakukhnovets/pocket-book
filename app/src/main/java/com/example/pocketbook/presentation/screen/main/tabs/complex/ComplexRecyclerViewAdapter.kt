package com.example.pocketbook.presentation.screen.main.tabs.complex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.data.classes.main.tabs.ImageHolderDataClass
import com.example.pocketbook.data.classes.main.tabs.TodayRecommendationHolderDataClass
import com.example.pocketbook.presentation.screen.main.tabs.complex.holders.ImageHolder
import com.example.pocketbook.presentation.screen.main.tabs.complex.holders.TodayRecommendationHolder
import com.example.pocketbook.presentation.screen.main.top.ItemListener

class ComplexRecyclerViewAdapter(
    private val context: Context,
    private val listOfObjects: MutableList<Any>,
    private val itemListener: ItemListener<Any>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val COLLECTION_OF_BOOKS = 0
    private val IMAGE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(context)
        val view: View
        when (viewType) {
            COLLECTION_OF_BOOKS -> {
                view = inflater.inflate(R.layout.main_screen_books_tab_collection_item, parent, false)
                holder = TodayRecommendationHolder(view, itemListener, listOfObjects)
            }
            IMAGE -> {
                view = inflater.inflate(R.layout.main_screen_books_tab_collection_item, parent, false)
                holder = ImageHolder(view, itemListener, listOfObjects)
            }
            else -> {
                view = inflater.inflate(R.layout.main_screen_books_tab_collection_item, parent, false)
                holder = TodayRecommendationHolder(view, itemListener, listOfObjects)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            IMAGE -> {
                val model = listOfObjects[position] as ImageHolderDataClass
                holder as ImageHolder
                holder.bindClickedImage()
                holder.binding.mainBooksTabItemTwoImage.setImageDrawable(model.image)
            }
            else -> {
                val model = listOfObjects[position] as TodayRecommendationHolderDataClass
                holder as TodayRecommendationHolder
                holder.bindBooks()
                holder.binding.bookTabUserImage.setImageDrawable(model.image)
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfObjects.size
    }
}