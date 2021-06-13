package com.example.pocketbook.presentation.screen.main.books_compilation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pocketbook.data.network.response.ImageCollectionResponse

class BooksCompilationAdapter(
    childFragmentManager: FragmentManager,
    private val list: List<ImageCollectionResponse>
) :
    FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 9
    }

    override fun getItem(position: Int): Fragment {
        return BooksCompilationItem.getInstance(
            list[position].id,
            list[position].tabText,
            list[position].headerText,
            list[position].buttonText,
            list[position].backGroundImageUrl
        )
    }
}
//TODO onPageChanged in the viewpager переопределение в коллбэке по позиции
//TODO behaviour coordinatorLayout + appBar + collapsingToolbar+ toolbar