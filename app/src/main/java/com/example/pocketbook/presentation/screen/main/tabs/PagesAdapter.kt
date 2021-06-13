package com.example.pocketbook.presentation.screen.main.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pocketbook.presentation.screen.main.tabs.audio_book.AudioBookPageFragment
import com.example.pocketbook.presentation.screen.main.tabs.book.BookPageFragment
class PagesAdapter(childFragmentManager: FragmentManager) :
    FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> BookPageFragment.getInstance()
            1 -> AudioBookPageFragment.getInstance()
        }
        return BookPageFragment.getInstance()
    }
}