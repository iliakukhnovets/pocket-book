package com.example.pocketbook.presentation.screen.main.books_compilation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.network.response.ImageCollectionResponse
import com.example.pocketbook.databinding.BooksCompilationScreenBinding
import com.example.pocketbook.presentation.screen.main.MainActivity
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksCompilationFragment() : Fragment(), ItemListener<ImageCollectionResponse> {

    companion object {
        const val IMAGE_ID = "imgId"

        fun getInstance(
            clickedId: Int
        ): BooksCompilationFragment {
            val fragment =
                BooksCompilationFragment()
            fragment.arguments?.apply {
                putString(IMAGE_ID, clickedId.toString())
            }
            return fragment
        }
    }

    private lateinit var binding: BooksCompilationScreenBinding
    private var clickedId = " "

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BooksCompilationScreenBinding.inflate(layoutInflater, container, false)
        clickedId = arguments?.getString(IMAGE_ID).toString()
        getArgs()
        (activity as MainActivity).binding.mainActivityBottomRecyclerView.visibility = View.GONE
        return binding.root
    }

    private fun getArgs() {
        NetworkClient.buildImageCollectionClient().getUrls().enqueue(
            object : Callback<List<ImageCollectionResponse>> {
                override fun onResponse(
                    call: Call<List<ImageCollectionResponse>>,
                    response: Response<List<ImageCollectionResponse>>
                ) {
                    setCollectionViewPager(response)
                }

                override fun onFailure(call: Call<List<ImageCollectionResponse>>, t: Throwable) {

                }

            })
    }

    private fun setCollectionViewPager(response: Response<List<ImageCollectionResponse>>) {
        val list = response.body()
        val viewPagerAdapter = binding.booksCompilationScreenViewPager
        if (list != null) {
            val pagesAdapter = BooksCompilationAdapter(
                childFragmentManager, list
            )
            viewPagerAdapter.adapter = pagesAdapter
        }
    }

    override fun itemClicked(response: ImageCollectionResponse) {
        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
    }
}