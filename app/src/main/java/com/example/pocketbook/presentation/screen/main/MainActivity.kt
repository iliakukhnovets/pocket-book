package com.example.pocketbook.presentation.screen.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketbook.R
import com.example.pocketbook.data.classes.main.bottom.MenuDataClass
import com.example.pocketbook.databinding.MainActivityBinding
import com.example.pocketbook.presentation.screen.main.bottom.BottomBarAdapter
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.my_books.MyBooksFragment
import com.example.pocketbook.presentation.screen.profile.UserProfileFragment
import com.example.pocketbook.presentation.screen.search.SearchScreenFragment

class MainActivity : AppCompatActivity(), ItemListener<MenuDataClass> {

    companion object {
        const val LOAD_ERROR = "Ошибка подключения к интернету. Проверьте подключение"
        const val DATA_FAIL = "Ошибка получения данных"
    }

    lateinit var binding: MainActivityBinding
    private var fragmentManager: FragmentManager = supportFragmentManager
    private val menuList = mutableListOf<MenuDataClass>()

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = MainActivityBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        if (bundle == null) {
            changeFragment(MainFragment.getInstance())
        }
        setBottomBarRecyclerView()
    }

    private fun changeFragment(fragment: Fragment) {
        val replace: FragmentTransaction =
            fragmentManager.beginTransaction().replace(R.id.main_frame, fragment)
        replace.addToBackStack(null).commit()
    }

    private fun initRecyclerView() {
        addItem(0, R.drawable.ic_bottom_nav_book_24, R.string.main_screen_bottom_nav_item_my_books)
        addItem(1, R.drawable.ic_bottom_nav_home_24, R.string.main_screen_bottom_nav_item_choose)
        addItem(2, R.drawable.ic_bottom_nav_search_24, R.string.main_screen_bottom_nav_item_search)
        addItem(
            3,
            R.drawable.ic_bottom_nav_profile_24,
            R.string.main_screen_bottom_nav_item_profile
        )
    }

    private fun addItem(index: Int, drawable: Int, text: Int) {
        menuList.add(index, MenuDataClass(drawable, getString(text)))
    }

    private fun setBottomBarRecyclerView() {
        binding.mainActivityBottomRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = BottomBarAdapter(this, menuList, this)
        binding.mainActivityBottomRecyclerView.adapter = adapter
        binding.mainActivityBottomRecyclerView.setHasFixedSize(true)
    }

    override fun itemClicked(model: MenuDataClass) {
        when (model.text) {
            getString(R.string.main_screen_bottom_nav_item_profile) -> {
                changeFragment(UserProfileFragment.getInstance())
            }
            getString(R.string.main_screen_bottom_nav_item_my_books) -> changeFragment(
                MyBooksFragment.getInstance()
            )
            getString(R.string.main_screen_bottom_nav_item_search) -> changeFragment(
                SearchScreenFragment.getInstance()
            )
            getString(R.string.main_screen_bottom_nav_item_choose) -> changeFragment(MainFragment.getInstance())
        }
    }
}