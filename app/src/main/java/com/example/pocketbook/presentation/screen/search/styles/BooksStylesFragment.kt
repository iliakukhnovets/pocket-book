package com.example.pocketbook.presentation.screen.search.styles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.classes.search.BookCategoriesDataClass
import com.example.pocketbook.databinding.BookCategoriesScreenBinding
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.search.SearchScreenFragment
import com.example.pocketbook.presentation.screen.search.category.CategoryFragment
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksStylesFragment : Fragment(), ItemListener<BookCategoriesDataClass> {

    companion object {

        const val KEY_CATEGORY = "CATEGORY"
        const val KEY_CATEGORY_COUNT = "CATEGORY_COUNT"
        const val KEY_CATEGORY_STYLE = "CATEGORY_STYLE"


        fun getInstance(styleTag: String, booksCount: String): BooksStylesFragment {
            val fragment = BooksStylesFragment()
            val bundle = Bundle()
            bundle.putString(KEY_CATEGORY, styleTag)
            bundle.putString(KEY_CATEGORY_COUNT, booksCount)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val styleProperty = "style"
    private val countProperty = "Count"
    private val category = "category"
    private var categoryTag = " "

    private lateinit var binding: BookCategoriesScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BookCategoriesScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        getCategoryArguments()
        setClickListeners()
        return view
    }

    override fun onResume() {
        super.onResume()
        getBookSCategories()

    }

    private fun getCategoryArguments() {
        categoryTag = arguments?.getString(KEY_CATEGORY).toString()
        binding.booksCategoriesTag.text = categoryTag
        binding.booksCategoriesCount.text = arguments?.getString(KEY_CATEGORY_COUNT)
        context?.let {
            Glide.with(it).load(R.mipmap.books_small).transform(
                RoundedCornersTransformation(
                    10,
                    10,
                    RoundedCornersTransformation.CornerType.ALL
                )
            ).into(binding.booksCategoriesImage)
        }
    }

    private fun setClickListeners() {
        binding.searchScreenToolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_frame, SearchScreenFragment.getInstance())?.commit()
        }
    }

    private fun getBookSCategories() {
        NetworkClient.buildBookApiClient()
            .getBookCategories(
                "$styleProperty='$categoryTag'",
                category,
                "$countProperty($category)",
                category
            ).enqueue(
                object : Callback<List<BookCategoriesDataClass>> {
                    override fun onResponse(
                        call: Call<List<BookCategoriesDataClass>>,
                        response: Response<List<BookCategoriesDataClass>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            setRecyclerView(response)
                        } else if (response.errorBody() != null) {
                            showMessage(DATA_FAIL)
                        }
                    }

                    override fun onFailure(
                        call: Call<List<BookCategoriesDataClass>>,
                        t: Throwable
                    ) {
                        TODO("Not yet implemented")
                    }
                }
            )
    }

    private fun setRecyclerView(response: Response<List<BookCategoriesDataClass>>) {
        binding.booksCategoriesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val list = response.body()
        if (list != null) {
            val adapter = context?.let {
                com.example.pocketbook.presentation.screen.search.styles.BooksStylesAdapter(
                    it,
                    list,
                    this
                )
            }
            binding.booksCategoriesRecyclerView.adapter = adapter
        }
    }

    override fun itemClicked(model: BookCategoriesDataClass) {
        val fragment =
            CategoryFragment.getInstance("", categoryTag, model.categoryCount, model.categoryTag)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_frame, fragment)?.commit()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
