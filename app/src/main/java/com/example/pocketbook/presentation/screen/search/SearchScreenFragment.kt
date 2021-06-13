package com.example.pocketbook.presentation.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.classes.search.BooksDataClass
import com.example.pocketbook.data.classes.search.BooksStylesDataClass
import com.example.pocketbook.databinding.SearchScreenBinding
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.search.styles.BooksStylesFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchScreenFragment : Fragment(), ItemListener<BooksStylesDataClass> {

    companion object {
        fun getInstance(): SearchScreenFragment {
            return com.example.pocketbook.presentation.screen.search.SearchScreenFragment()
        }
    }

    private lateinit var binding: SearchScreenBinding
    private var listOfBooks = mutableListOf<BooksDataClass>()
    private val styleProperty = "style"
    private val countProperty = "Count"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        createBooksData()
        setBooksRecyclerView()
        getBooksStyles()
        return view
    }

    private fun createBooksData() {
        addListOfBooksItem(0, 0, R.drawable.ic_search_screen_headphones_24, "Аудиокниги")
        addListOfBooksItem(1, 0, R.drawable.ic_search_screen_free_books_24, "Бесплатные книги")
        addListOfBooksItem(2, 0, R.drawable.ic_search_screen_podcast_24, "Подкасты")
        addListOfBooksItem(3, 0, R.drawable.ic_my_books_read_24, "Читай и слушай")
    }

    private fun addListOfBooksItem(
        index: Int,
        booksCount: Int,
        vectorImage: Int,
        category: String
    ) {
        listOfBooks.add(
            index,
            BooksDataClass(
                booksCount,
                ResourcesCompat.getDrawable(resources, vectorImage, null),
                category
            )
        )
    }

    private fun setBooksRecyclerView() {
        binding.searchScreenBooksRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = context?.let {
            com.example.pocketbook.presentation.screen.search.BooksAdapter(
                it,
                listOfBooks
            )
        }
        binding.searchScreenBooksRecyclerView.adapter = adapter
        val divider = DividerItemDecoration(context, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.recycler_view_divider, null)?.let {
            divider.setDrawable(
                it
            )
        }
        binding.searchScreenBooksRecyclerView.addItemDecoration(divider)
    }

    private fun getBooksStyles() {
        NetworkClient.buildBookApiClient().getBookStyles(
            styleProperty,
            "$countProperty('$styleProperty')",
            styleProperty
        ).enqueue(
            object : Callback<List<BooksStylesDataClass>> {
                override fun onResponse(
                    call: Call<List<BooksStylesDataClass>>,
                    response: Response<List<BooksStylesDataClass>>
                ) {
                    setBooksCategoriesRecyclerView(response)
                }

                override fun onFailure(call: Call<List<BooksStylesDataClass>>, t: Throwable) {
                    showMessage(DATA_FAIL)
                }
            }
        )
    }

    private fun setBooksCategoriesRecyclerView(response: Response<List<BooksStylesDataClass>>) {
        val linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchScreenBookCategoriesRecyclerView.layoutManager = linearLayout
        val list = response.body()
        if (list != null) {
            val adapter = context?.let {
                com.example.pocketbook.presentation.screen.search.SearchBookStylesAdapter(
                    it,
                    list,
                    this
                )
            }
            binding.searchScreenBookCategoriesRecyclerView.adapter = adapter
        }
    }

    override fun itemClicked(model: BooksStylesDataClass) {
        val fragment = BooksStylesFragment.getInstance(model.style, model.categoryCount)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_frame, fragment)?.commit()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}