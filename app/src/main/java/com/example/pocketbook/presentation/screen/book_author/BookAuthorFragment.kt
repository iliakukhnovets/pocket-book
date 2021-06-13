package com.example.pocketbook.presentation.screen.book_author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketbook.R
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.network.api_service.book_author.BookAuthorNetworkService
import com.example.pocketbook.data.network.response.BookAuthorResponse
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.databinding.BookAuthorScreenBinding
import com.example.pocketbook.presentation.screen.book.SelectedBookFragment
import com.example.pocketbook.presentation.screen.book_author.recycler_view.related_authors.RelatedAuthorsAdapter
import com.example.pocketbook.presentation.screen.book_author.recycler_view.related_books.RelatedBooksAdapter
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.LOAD_ERROR
import com.example.pocketbook.presentation.screen.main.top.ImageListener
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.my_books.MyBooksFragment
import com.example.pocketbook.presentation.screen.search.category.CategoryFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookAuthorFragment : androidx.fragment.app.Fragment(), ItemListener<BookResponse>,
    ImageListener {

    companion object {
        const val BOOK_AUTHOR_ARG = "bookAuthor"
        const val BOOK_AUTHOR_STYLE = "bookAuthorStyle"

        fun getInstance(bookAuthorName: String, bookStyle: String): BookAuthorFragment {
            val arguments = Bundle()
            val fragment = BookAuthorFragment()
            arguments.putString(BOOK_AUTHOR_ARG, bookAuthorName)
            arguments.putString(BOOK_AUTHOR_STYLE, bookStyle)
            fragment.arguments = arguments
            return fragment
        }
    }

    private val author = "author"
    private val FAILED_TO_LOAD_RELATED_AUTHORS = "Failed to load related authors"
    private lateinit var binding: BookAuthorScreenBinding
    private var authorName: String = ""
    private var bookStyle: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BookAuthorScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        getAuthorName()
        setClickListeners()
        getBookAuthorBooks()
        loadRelatedAuthors()
        loadBookAuthorBooks()
        return view
    }

    override fun onResume() {
        super.onResume()
        getAuthorName()
        loadAuthorData()
    }

    private fun getAuthorName() {
        authorName = arguments?.getString(BOOK_AUTHOR_ARG).toString()
        bookStyle = arguments?.getString(BOOK_AUTHOR_STYLE).toString()
        binding.bookAuthorName.text = authorName
    }

    private fun setClickListeners() {
        binding.bookAuthorToolbar.setNavigationOnClickListener {
            changeFragment(MyBooksFragment.getInstance())
        }
        binding.bookAuthorAllBooksBtn.setOnClickListener {
            changeFragment(CategoryFragment.getInstance(authorName, "", "", ""))
        }
    }

    private fun getBookAuthorBooks() {
        NetworkClient
            .buildBookApiClient()
            .getRelatedBookAuthorBooks("author='$authorName'")
            .enqueue(
                object : Callback<List<BookResponse>> {
                    override fun onResponse(
                        call: Call<List<BookResponse>>,
                        response: Response<List<BookResponse>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            setRecyclerViewAdapter(response)
                        }
                    }

                    override fun onFailure(call: Call<List<BookResponse>>, t: Throwable) {
                        showToast(LOAD_ERROR)
                    }

                }
            )
    }

    private fun setRecyclerViewAdapter(response: Response<List<BookResponse>>) {
        binding.bookAuthorAllBooksRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val list = response.body()
        if (list != null) {
            val adapter = RelatedBooksAdapter(context, list, this)
            binding.bookAuthorAllBooksRecyclerView.adapter = adapter
        }
    }

    private fun loadRelatedAuthors() {
        NetworkClient
            .buildBookAuthorApi()
            .getRelatedBookAuthor("style='$bookStyle'")
            .enqueue(
                object : Callback<List<BookAuthorResponse>> {
                    override fun onResponse(
                        call: Call<List<BookAuthorResponse>>,
                        response: Response<List<BookAuthorResponse>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            getRelatedAuthors(response)
                        }
                    }

                    override fun onFailure(call: Call<List<BookAuthorResponse>>, t: Throwable) {
                        showToast(FAILED_TO_LOAD_RELATED_AUTHORS)
                    }
                }
            )
    }

    private fun getRelatedAuthors(response: Response<List<BookAuthorResponse>>) {
        binding.bookAuthorRelatedAuthorsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val list = response.body()
        if (list != null) {
            val adapter = RelatedAuthorsAdapter(context, list, this)
            binding.bookAuthorRelatedAuthorsRecyclerView.adapter = adapter
        }
    }

    private fun loadBookAuthorBooks() {
        BookAuthorNetworkService().loadBookAuthorBooksCount(authorName, author, context, binding)
    }

    private fun loadAuthorData() {
        context?.let { BookAuthorNetworkService().loadAuthorData(it, authorName, binding) }
    }

    private fun changeFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_frame, fragment)?.commit()
    }


    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun itemClicked(model: BookResponse) {
        changeFragment(
            SelectedBookFragment.getInstance(model)
        )
    }

    override fun imageClicked(response: BookAuthorResponse) {
        changeFragment(getInstance(response.bookAuthor, bookStyle))
    }
}