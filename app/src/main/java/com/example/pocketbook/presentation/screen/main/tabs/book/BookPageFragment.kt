package com.example.pocketbook.presentation.screen.main.tabs.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketbook.R
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.classes.book.SelectedBooksDataClass
import com.example.pocketbook.data.classes.main.tabs.ButtonsHolderDataClass
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.databinding.MainScreenBooksTabBinding
import com.example.pocketbook.presentation.screen.book.SelectedBookFragment
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.LOAD_ERROR
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.my_books.top.MyBooksTopListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class BookPageFragment : Fragment(), ItemListener<BookResponse> {
    companion object {
        fun getInstance(): BookPageFragment {
            return BookPageFragment()
        }
    }

    private lateinit var binding: MainScreenBooksTabBinding
    private val book = "book"
    private val sortingColumn = "name"
    private var listOfButtons = mutableListOf<ButtonsHolderDataClass>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainScreenBooksTabBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        initButtonsCollection()
        getCollectionOfBooks()
        getButtonsCollection()
        return view
    }

    private fun getCollectionOfBooks() {
        NetworkClient.buildBookApiClient()
            .getAllBooks(10, getRandomOffset(), "type='$book'", sortingColumn)
            .enqueue(
                object : Callback<List<BookResponse>> {
                    override fun onResponse(
                        call: Call<List<BookResponse>>,
                        response: Response<List<BookResponse>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            loadBooks(response)
                        } else {
                            showToast(DATA_FAIL)
                        }
                    }

                    override fun onFailure(call: Call<List<BookResponse>>, t: Throwable) {
                        showToast(LOAD_ERROR)
                    }

                }
            )
    }

    private fun getRandomOffset(): Int {
        return Random.nextInt(0, 30)
    }

    private fun loadBooks(response: Response<List<BookResponse>>) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.mainBooksPageRelativeBooksRecyclerView.layoutManager = layoutManager
        val list: List<BookResponse>? = response.body()
        val adapter = list?.let { MyBooksTopListAdapter(context, this, it) }
        binding.mainBooksPageRelativeBooksRecyclerView.adapter = adapter
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun initButtonsCollection() {
        addItem(0, R.string.main_screen_books_tab_new_books, R.drawable.ic_main_tab_new_releases_24)
        addItem(1, R.string.main_screen_books_tab_top_books, R.drawable.ic_my_books_read_24)
        addItem(
            2,
            R.string.main_screen_books_tab_free_books,
            R.drawable.ic_search_screen_free_books_24
        )
        addItem(
            3,
            R.string.main_screen_books_tab_top_audio_books,
            R.drawable.ic_search_screen_headphones_24
        )
    }

    private fun addItem(index: Int, string: Int, image: Int) {
        listOfButtons.add(index, ButtonsHolderDataClass(getString(string), image))
    }

    private fun getButtonsCollection() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.mainBooksPageButtonsRecyclerView.layoutManager = layoutManager
        binding.mainBooksPageButtonsRecyclerView.adapter =
            com.example.pocketbook.presentation.screen.main.tabs.book.ButtonCollectionAdapter(
                context,
                listOfButtons
            )
    }

    override fun itemClicked(response: BookResponse) {
        val supportFragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.main_frame,
                SelectedBookFragment.getInstance(response)
            )
        fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction?.commit()
    }
}