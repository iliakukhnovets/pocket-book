package com.example.pocketbook.presentation.screen.search.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketbook.R
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.databinding.SelectedCategoryScreenBinding
import com.example.pocketbook.presentation.screen.book.SelectedBookFragment
import com.example.pocketbook.presentation.screen.book_author.BookAuthorFragment.Companion.BOOK_AUTHOR_ARG
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.LOAD_ERROR
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.my_books.info.recycler_view.MyBooksInfoAdapter
import com.example.pocketbook.presentation.screen.search.styles.BooksStylesFragment
import com.example.pocketbook.presentation.screen.search.styles.BooksStylesFragment.Companion.KEY_CATEGORY
import com.example.pocketbook.presentation.screen.search.styles.BooksStylesFragment.Companion.KEY_CATEGORY_COUNT
import com.example.pocketbook.presentation.screen.search.styles.BooksStylesFragment.Companion.KEY_CATEGORY_STYLE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment(), ItemListener<BookResponse> {

    companion object {

        fun getInstance(
            author: String,
            category: String,
            categoryCount: String,
            style: String
        ): CategoryFragment {
            val fragment =
                com.example.pocketbook.presentation.screen.search.category.CategoryFragment()
            val bundle = Bundle()
            bundle.putString(BOOK_AUTHOR_ARG, author)
            bundle.putString(KEY_CATEGORY, category)
            bundle.putString(KEY_CATEGORY_COUNT, categoryCount)
            bundle.putString(KEY_CATEGORY_STYLE, style)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val sortingTagAuthor = "author"
    private val sortingTagStyle = "style"
    private val sortingTagCategory = "category"
    lateinit var binding: SelectedCategoryScreenBinding
    private var bookStyle = ""
    private var bookAuthor = ""
    private var bookCategory = ""
    private var bookCategoryCount = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SelectedCategoryScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        getFragmentArguments()
        setClickListeners()
        loadServerData(bookStyle, bookAuthor)
        loadCategoryBooks()
        setFields()
        return view
    }

    private fun getFragmentArguments() {
        bookAuthor = arguments?.getString(BOOK_AUTHOR_ARG).toString()
        bookCategory = arguments?.getString(KEY_CATEGORY).toString()
        bookCategoryCount = arguments?.getString(KEY_CATEGORY_COUNT).toString()
        bookStyle = arguments?.getString(KEY_CATEGORY_STYLE).toString()
    }

    private fun setClickListeners() {
        binding.toolbar.setNavigationOnClickListener {
            changeFragment(BooksStylesFragment.getInstance(bookCategory, bookCategoryCount))
        }
    }

    private fun setFields() {
        val builder = StringBuilder()
        builder.append(bookCategoryCount).append(" ").append("книг")
        binding.toolbar.title = bookCategory
        binding.categoryScreenCollapsingToolBar.title = builder.toString()
    }

    private fun loadServerData(bookStyle: String, bookAuthor: String) {
        when {
            bookStyle == " " -> load(bookAuthor, sortingTagAuthor)
            bookStyle != " " -> load(bookStyle, sortingTagCategory)
        }
    }

    private fun loadCategoryBooks() {
        if (bookCategory != "" && bookStyle == "") {
            load(bookCategory, sortingTagStyle)
            binding.toolbar.title = bookCategory
        } else {
            load(bookAuthor, sortingTagAuthor)
            binding.toolbar.title = bookAuthor
        }
    }

    private fun load(argument: String, sortingTag: String) {
        NetworkClient.buildBookApiClient().getRelatedBooks("$sortingTag='$argument'").enqueue(
            object : Callback<List<BookResponse>> {
                override fun onResponse(
                    call: Call<List<BookResponse>>,
                    response: Response<List<BookResponse>>
                ) {
                    setRecyclerView(response)
                }

                override fun onFailure(call: Call<List<BookResponse>>, t: Throwable) {
                    showMessage(LOAD_ERROR)
                }

            }
        )
    }

    private fun setRecyclerView(response: Response<List<BookResponse>>) {
        binding.selectedCategoryRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val list = response.body()
        val adapter = list?.let { MyBooksInfoAdapter(context, this, it) }
        binding.selectedCategoryRecyclerView.adapter = adapter
    }

    private fun changeFragment(fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.main_frame, fragment)
            ?.commit()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun itemClicked(response: BookResponse) {
        changeFragment(
            SelectedBookFragment.getInstance(response)
        )
    }
}