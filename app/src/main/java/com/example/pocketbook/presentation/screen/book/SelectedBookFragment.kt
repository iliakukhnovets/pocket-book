package com.example.pocketbook.presentation.screen.book

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
import com.example.pocketbook.data.classes.book.SelectedBooksDataClass.Companion.SELECTED_BOOK_FACTS
import com.example.pocketbook.data.classes.book.SelectedBooksDataClass.Companion.SELECTED_BOOK_MARKS_COUNT
import com.example.pocketbook.data.classes.book.SelectedBooksDataClass.Companion.SELECTED_BOOK_SERIES_ARG
import com.example.pocketbook.data.network.response.BookAuthorResponse
import com.example.pocketbook.data.network.response.BookResponse
import com.example.pocketbook.databinding.SelectedBookScreenBinding
import com.example.pocketbook.presentation.screen.book.recycler_view.BookSeriesAdapter
import com.example.pocketbook.presentation.screen.book_author.BookAuthorFragment
import com.example.pocketbook.presentation.screen.book_author.recycler_view.related_books.RelatedBooksAdapter
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.my_books.MyBooksFragment
import com.example.pocketbook.presentation.screen.search.category.CategoryFragment
import com.example.pocketbook.presentation.screen.subscribe.SubscribeFragment
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectedBookFragment : Fragment(), ItemListener<BookResponse> {

    companion object {
        fun getInstance(response: BookResponse): SelectedBookFragment {
            val fragment = SelectedBookFragment()
            val bundle = Bundle()
            bundle.putParcelable("bookData", response)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val failedToLoadBookSeries = "Ошибка при получении серии книг"
    private val failedToLoadAuthorData = "Ошибка при получении данных автора"
    private val failedToLoadRelatedBooks = "Ошибка при получении похожих книг"
    private val failedToLoadBooksCount = "Ошибка при получении количества книг"
    private val sortingOrder = "asc"
    private val style = "style"
    lateinit var binding: SelectedBookScreenBinding
    private var bookUrl: String = ""
    private var isFinishedFlag: String = ""
    private var bookName: String = ""
    private var bookAuthor: String = " "
    private var bookMarkSum: Int = 0
    private var bookMarkCount: Int = 0
    private var isRated: Boolean = false
    private var bookRating = 0
    private var ageLimit = " "
    private var bookSubscribe = " "
    private var bookAnnotation = " "
    private var bookStyle = " "
    private var bookLanguage = " "
    private var bookSeries: String = " "
    private var bookAuthorStyle: String = " "
    private var bookFacts: String = " "
    private var styleBooksCount: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SelectedBookScreenBinding.inflate(inflater, container, false)
        initializeFields()
        setBookFields()
        loadBookStyleCount()
        setOnClickListeners()
        return binding.root
    }

    private fun initializeFields() {
        val bundle = this.arguments
        val model: BookResponse? = bundle?.getParcelable("bookData")
        if (model != null) {
            bookUrl = model.imageUrl
            isFinishedFlag = model.isBookFinished
            bookName = model.bookName
            bookAuthor = model.bookAuthor
            bookRating = model.bookRating
            bookMarkSum = model.marksSum
            bookMarkCount = model.marksCount
            ageLimit = model.ageLimit
            bookSubscribe = model.typeOfBookSubscribe
            isRated = model.isRated
            bookAnnotation = model.bookAnnotation
            bookStyle = model.bookStyle
            bookLanguage = model.bookLanguage
            if (getArgument(SELECTED_BOOK_FACTS) != null) {
                bookFacts = model.facts
            }
            if (getArgument(SELECTED_BOOK_SERIES_ARG) != null) {
                bookSeries = model.bookSeries
            }
        }
    }

    private fun getArgument(argumentKey: String?): Any? {
        return arguments?.get(argumentKey)
    }

    private fun setBookFields() {
        binding.selectedBookName.text = bookName
        binding.selectedBookAuthor.text = bookAuthor
        binding.selectedBookRatingMark.text = bookRating.toString()
        binding.selectedBookRating.rating = bookRating.toFloat()
        binding.selectedBookAgeLimit.text = ageLimit
        binding.selectedBookSubscribeBtn.text = bookSubscribe
        binding.selectedBookAnnotation.text = bookAnnotation
        binding.selectedBookStyleTag.text = bookStyle
        binding.selectedBookLanguage.text = bookLanguage
        binding.selectedBookSeriesTag.text = bookSeries
        binding.selectedBookInterestedFactsText.text = bookFacts
        setVisibility(bookSeries)
        setFactVisibility(bookFacts)
    }

    private fun loadBookStyleCount() {
        NetworkClient.buildBookApiClient()
            .getBooksCountProperty("style='$bookStyle'", "Count($style)")
            .enqueue(
                object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful && response.body() != null) {
                            getBooksCount(response)
                        } else if (response.errorBody() != null) {
                            showToast(DATA_FAIL)
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        showToast(failedToLoadBooksCount)
                    }
                }
            )
    }

    private fun getBooksCount(response: Response<String>) {
        styleBooksCount = response.body().toString()
    }

    private fun setOnClickListeners() {
        binding.selectedBookSubscribeBtn.setOnClickListener {
            changeFragment(SubscribeFragment.getInstance())
        }

        binding.selectedBookToolbar.setNavigationOnClickListener {
            changeFragment(MyBooksFragment.getInstance())
        }
        binding.selectedBookAuthor.setOnClickListener {
            changeFragment(BookAuthorFragment.getInstance(bookAuthor, bookAuthorStyle))
        }
        binding.selectedBookAddPremiumCard.setOnClickListener {
            changeFragment(SubscribeFragment.getInstance())
        }
        binding.selectedBookDefaultRating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            showToast("$rating")
        }
        binding.selectedBookStyleTag.setOnClickListener {
            changeFragment(CategoryFragment.getInstance("", bookStyle, styleBooksCount, ""))
        }
    }

    override fun onStart() {
        super.onStart()
        loadRelatedBooks()
        loadBookSeries()
        loadTopScreenImages(bookUrl)
        loadAuthorData()
        setRatingField()
        setBookStatus()
    }

    private fun loadRelatedBooks() {
        NetworkClient.buildBookApiClient()
            .getRelatedBooks("style='$bookStyle'").enqueue(
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
                        showToast(failedToLoadRelatedBooks)
                    }

                }
            )
    }

    private fun setRecyclerViewAdapter(response: Response<List<BookResponse>>) {
        binding.selectedBookRelatedBookRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val list = response.body()
        val relatedBooksAdapter = list?.let { RelatedBooksAdapter(context, it, this) }
        binding.selectedBookRelatedBookRecyclerView.adapter = relatedBooksAdapter
    }

    private fun loadBookSeries() {
        NetworkClient.buildBookApiClient()
            .getBookAuthorSeries(
                "series='$bookSeries'",
                "series_order_tag='$sortingOrder'"
            ).enqueue(
                object : Callback<List<BookResponse>> {
                    override fun onResponse(
                        call: Call<List<BookResponse>>,
                        response: Response<List<BookResponse>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            setBookSeriesRecyclerViewAdapter(response)
                        }
                    }

                    override fun onFailure(call: Call<List<BookResponse>>, t: Throwable) {
                        showToast(failedToLoadBookSeries)
                    }

                }
            )
    }

    private fun setBookSeriesRecyclerViewAdapter(response: Response<List<BookResponse>>) {
        binding.selectedBookRelatedSeriesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val list = response.body()
        val adapter = list?.let { BookSeriesAdapter(context, it, this) }
        binding.selectedBookRelatedSeriesRecyclerView.adapter = adapter
    }

    private fun loadTopScreenImages(bookUrl: String?) {
        activity?.let {
            Glide.with(it)
                .load(bookUrl)
                .transform(RoundedCornersTransformation(10, 10))
                .into(binding.selectedBookImage)
        }

        activity?.let {
            Glide.with(it)
                .load(bookUrl)
                .transform(BlurTransformation(10))
                .into(binding.selectedBookToolbarBackground)
        }
    }

    private fun loadAuthorData() {
        NetworkClient
            .buildBookAuthorApi()
            .getBookAuthor("author='$bookAuthor'")
            .enqueue(
                object : Callback<List<BookAuthorResponse>> {
                    override fun onResponse(
                        call: Call<List<BookAuthorResponse>>,
                        response: Response<List<BookAuthorResponse>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            getBookAuthor(response)
                        } else if (response.errorBody() != null) {
                            showToast(DATA_FAIL)
                        }
                    }

                    override fun onFailure(call: Call<List<BookAuthorResponse>>, t: Throwable) {
                        showToast(failedToLoadAuthorData)
                    }

                }
            )
    }

    private fun getBookAuthor(response: Response<List<BookAuthorResponse>>) {
        val list = response.body()
        if (list != null) {
            bookAuthorStyle = list[0].bookAuthorStyle
        }
    }

    private fun setRatingField() {
        val builder = StringBuilder()
        val result = arguments?.getInt(SELECTED_BOOK_MARKS_COUNT)
        builder.append(result).append(" ").append("читателей оценили")
        binding.selectedBookReadersNumber.text = builder.toString()
    }

    private fun setBookStatus() {
        binding.selectedBookStatus.text = isFinishedFlag
    }

    private fun setVisibility(bookSeries: String) {
        when {
            bookSeries == " " -> setSeriesVisibility(View.GONE)
            bookSeries != null -> setSeriesVisibility(View.VISIBLE)
        }
    }

    private fun setFactVisibility(fact: String) {
        when {
            fact == " " -> setBookFactsVisibility(View.GONE)
            fact != null -> setBookFactsVisibility(View.VISIBLE)
        }
    }

    private fun setSeriesVisibility(visibility: Int) {
        binding.selectedBookSeriesTag.visibility = visibility
        binding.appCompatTextView8.visibility = visibility
        binding.selectedBookRelatedSeriesRecyclerView.visibility = visibility
    }

    private fun setBookFactsVisibility(visibility: Int) {
        binding.selectedBookInterestedFacts.visibility = visibility
        binding.selectedBookInterestedFactsText.visibility = visibility
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun changeFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_frame, fragment)?.commit()
    }

    override fun itemClicked(model: BookResponse) {
        changeFragment(
            getInstance(model)
        )
    }
}