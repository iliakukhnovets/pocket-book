package com.example.pocketbook.presentation.screen.my_books.info

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
import com.example.pocketbook.databinding.BookInfoScreenBinding
import com.example.pocketbook.presentation.screen.book.SelectedBookFragment
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.my_books.MyBooksFragment
import com.example.pocketbook.presentation.screen.my_books.MyBooksFragment.Companion.ALL_BOOKS_LOAD_FLAG
import com.example.pocketbook.presentation.screen.my_books.MyBooksFragment.Companion.SUB_TITLE_STRING_KEY
import com.example.pocketbook.presentation.screen.my_books.MyBooksFragment.Companion.TITLE_STRING_KEY
import com.example.pocketbook.presentation.screen.my_books.info.recycler_view.MyBooksInfoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBooksInfoFragment : Fragment(), ItemListener<BookResponse> {

    companion object {
        fun getInstance(): MyBooksInfoFragment {
            return MyBooksInfoFragment()
        }
    }

    private val bookType: String = "book"
    private val sortingColumn = "name"
    private val columnType = "type"
    private val columnIsFinishedFlag = "is_finished_flag"
    private var allBooksLoadFlag: Boolean? = null
    private lateinit var title: String
    private lateinit var subTitle: String
    private lateinit var binding: BookInfoScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BookInfoScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        setClickListeners()
        initializeToolbarFields()
        loadBooks()
        return view
    }

    override fun onResume() {
        super.onResume()
        setToolbarFields()
    }

    private fun setClickListeners() {
        binding.myBookInfoToolbar.setNavigationOnClickListener {
            changeFragment(MyBooksFragment.getInstance())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_frame, fragment)
            ?.addToBackStack("selected")?.commit()
    }

    private fun initializeToolbarFields() {
        title = arguments?.get(TITLE_STRING_KEY).toString()
        subTitle = arguments?.get(SUB_TITLE_STRING_KEY).toString()
        allBooksLoadFlag = arguments?.get(ALL_BOOKS_LOAD_FLAG) as Boolean
    }

    private fun setToolbarFields() {
        val stringBuilder = StringBuilder()
        stringBuilder.append(subTitle).append(" ").append("книг")
        binding.myBookInfoToolbar.title = title
        binding.collapsingToolbarSubTitle.text = stringBuilder.toString()
        binding.myBookInfoCollapsingToolbar.setExpandedTitleTextAppearance(R.style.TextAppearance_Expanded)
        binding.myBookInfoCollapsingToolbar.setCollapsedTitleTextAppearance(R.style.TextAppearance_Collapsed)
    }

    private fun loadBooks() {
        when (allBooksLoadFlag) {
            false -> loadAllBooks(columnIsFinishedFlag, title)
            true -> loadAllBooks(columnType, bookType)
        }
    }

    private fun loadAllBooks(column: String, columnValue: String) {
        NetworkClient.buildBookApiClient().getAllBooks(
            60,
            0,
            "$column ='$columnValue'",
            sortingColumn
        )
            .enqueue(
                object : Callback<List<BookResponse>> {
                    override fun onResponse(
                        call: Call<List<BookResponse>>,
                        response: Response<List<BookResponse>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            setRecyclerView(response)
                        }
                    }

                    override fun onFailure(call: Call<List<BookResponse>>, t: Throwable) {
                        showMessage(DATA_FAIL)
                    }
                }
            )
    }

    private fun setRecyclerView(response: Response<List<BookResponse>>) {
        binding.myBookInfoRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val list = response.body()
        if (list != null) {
            val adapter = MyBooksInfoAdapter(context, this, list)
            binding.myBookInfoRecyclerView.adapter = adapter
        }
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