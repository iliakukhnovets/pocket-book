package com.example.pocketbook.presentation.screen.my_books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.classes.my_books.BooksStatusDataClass
import com.example.pocketbook.data.network.api_service.book.BookNetworkService
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.databinding.MyBooksScreenBinding
import com.example.pocketbook.presentation.screen.book.SelectedBookFragment
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.LOAD_ERROR
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.my_books.books_status.BooksStatusAdapter
import com.example.pocketbook.presentation.screen.my_books.info.MyBooksInfoFragment
import com.example.pocketbook.presentation.screen.my_books.my_shelf.CreatedShelfFragment
import com.example.pocketbook.presentation.screen.my_books.my_shelf.DefaultShelfFragment
import com.example.pocketbook.presentation.screen.my_books.top.MyBooksTopListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBooksFragment : Fragment(), ItemListener<BookResponse>,
    BookMenuListener<BooksStatusDataClass> {
    companion object {
        fun getInstance(): MyBooksFragment {
            return com.example.pocketbook.presentation.screen.my_books.MyBooksFragment()
        }

        const val SUB_TITLE_STRING_KEY = "subTitleKey"
        const val TITLE_STRING_KEY = "titleKey"
        const val ALL_BOOKS_LOAD_FLAG = "loadFlag"
    }

    private val OFFLINE = "Доступны оффлайн"
    private val NO_BOOKS_AVAILABLE = "Нет"
    private val ALL_USER_BOOKS = "Все книги"
    private val isFinishedProperty = "is_finished_flag"
    private val countProperty = "Count"
    private lateinit var binding: MyBooksScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyBooksScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        setClickListeners()
        loadBookStatus()
        getAllUserBooksCount()
        return view
    }

    private fun setClickListeners() {
        binding.myBooksAvailableOffline.setOnClickListener {
            changeFragment(
                MyBooksInfoFragment.getInstance(),
                OFFLINE,
                binding.myBooksOfflineCount.text as String,
                false
            )
        }
        binding.myBooksAllUserBooks.setOnClickListener {
            changeFragment(
                MyBooksInfoFragment.getInstance(),
                ALL_USER_BOOKS,
                binding.myBooksAllUserBooks.text.toString(),
                true
            )
        }
    }

    private fun loadBookStatus() {
        NetworkClient.buildBookApiClient().getBooksStatus(
            isFinishedProperty,
            "$countProperty($isFinishedProperty)",
            isFinishedProperty
        ).enqueue(
            object : Callback<List<BooksStatusDataClass>> {
                override fun onResponse(
                    call: Call<List<BooksStatusDataClass>>,
                    response: Response<List<BooksStatusDataClass>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        setBooksStatusRecyclerView(response)
                    }
                }

                override fun onFailure(call: Call<List<BooksStatusDataClass>>, t: Throwable) {
                    showToast(DATA_FAIL)
                }

            }
        )
    }

    private fun setBooksStatusRecyclerView(response: Response<List<BooksStatusDataClass>>) {
        binding.myBooksBooksStatusRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val list = response.body()
        if (list != null) {
            val adapter = BooksStatusAdapter(context, this, list)
            val itemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            ResourcesCompat.getDrawable(resources, R.drawable.recycler_view_divider, null)?.let {
                itemDecoration.setDrawable(
                    it
                )
            }
            binding.myBooksBooksStatusRecyclerView.addItemDecoration(itemDecoration)
            binding.myBooksBooksStatusRecyclerView.adapter = adapter
        }
    }

    private fun getAllUserBooksCount() {
        BookNetworkService().loadAllUserBooks(context, binding)
    }


    private fun changeFragment(
        fragment: Fragment,
        title: String?,
        subTitle: String?,
        allBooksLoadFlag: Boolean
    ) {
        val arguments = Bundle()
        arguments.putString(TITLE_STRING_KEY, title)
        arguments.putBoolean(ALL_BOOKS_LOAD_FLAG, allBooksLoadFlag)
        if (subTitle.equals("0")) {
            arguments.putString(SUB_TITLE_STRING_KEY, NO_BOOKS_AVAILABLE)
        } else {
            arguments.putString(SUB_TITLE_STRING_KEY, subTitle)
        }
        fragment.arguments = arguments
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_frame, fragment)
            ?.commit()
    }

    override fun onResume() {
        super.onResume()
        val bundle = arguments
        if (bundle != null) {
            changeShelfFragment(CreatedShelfFragment.getInstance())
        } else {
            changeShelfFragment(DefaultShelfFragment.getInstance())
        }
        showBooks()

    }

    private fun changeShelfFragment(fragment: Fragment) {
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.my_books_shelves_frame, fragment)
            ?.commit()
    }

    private fun showBooks() {
        NetworkClient.buildBookApiClient().getBooks().enqueue(
            object : Callback<List<BookResponse>> {
                override fun onResponse(
                    call: Call<List<BookResponse>>,
                    response: Response<List<BookResponse>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        loadRecyclerViewItems(response)
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

    private fun loadRecyclerViewItems(response: Response<List<BookResponse>>) {
        binding.myBooksTopRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val list: List<BookResponse>? = response.body()
        val adapter = list?.let { MyBooksTopListAdapter(context, this, it) }
        binding.myBooksTopRecyclerView.adapter = adapter

    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun itemClicked(response: BookResponse) {
        val supportFragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.main_frame,
                SelectedBookFragment.getInstance(response)
            )
        fragmentTransaction?.setTransition(TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction?.commit()
    }

    override fun menuClicked(model: BooksStatusDataClass) {
        changeFragment(
            MyBooksInfoFragment.getInstance(),
            model.statusText,
            model.booksStatusCount.toString(),
            false
        )
    }
}