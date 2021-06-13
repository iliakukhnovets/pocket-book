package com.example.pocketbook.presentation.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.pocketbook.R
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.network.response.ImageCollectionResponse
import com.example.pocketbook.databinding.MainScreenBinding
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.LOAD_ERROR
import com.example.pocketbook.presentation.screen.main.books_compilation.BooksCompilationFragment
import com.example.pocketbook.presentation.screen.main.tabs.PagesAdapter
import com.example.pocketbook.presentation.screen.main.top.ItemListener
import com.example.pocketbook.presentation.screen.main.top.TopScreenAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment(), ItemListener<ImageCollectionResponse> {

    companion object {


        fun getInstance(): MainFragment {
            return com.example.pocketbook.presentation.screen.main.MainFragment()
        }
    }

    private lateinit var binding: MainScreenBinding

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        binding = MainScreenBinding.inflate(layoutInflater, viewGroup, false)
        showBookCollections()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setViewPage()
    }

    private fun setViewPage() {
        val mainViewPager: ViewPager = binding.mainFragmentViewPager
        val pagesAdapter = fragmentManager?.let { PagesAdapter(it) }
        mainViewPager.adapter = pagesAdapter
        binding.mainFragmentTabLayout.setupWithViewPager(mainViewPager)
    }

    private fun showBookCollections() {
        NetworkClient.buildImageCollectionClient().getUrls().enqueue(
            object : Callback<List<ImageCollectionResponse>> {
                override fun onResponse(
                    call: Call<List<ImageCollectionResponse>>,
                    response: Response<List<ImageCollectionResponse>>
                ) {
                    if (response.isSuccessful) {
                        setRecyclerViewAdapter(response)
                    } else {
                        showToast(DATA_FAIL)
                    }
                }

                override fun onFailure(call: Call<List<ImageCollectionResponse>>, t: Throwable) {
                    showToast(LOAD_ERROR)
                }
            }
        )
    }

    private fun setRecyclerViewAdapter(response: Response<List<ImageCollectionResponse>>) {
        binding.mainFragmentTopRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.mainFragmentTopRecyclerView.setHasFixedSize(true)
        val list = response.body()
        if (list != null) {
            val recyclerViewAdapter = TopScreenAdapter(context, this, list)
            binding.mainFragmentTopRecyclerView.adapter = recyclerViewAdapter
        }
    }

    private fun showToast(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun itemClicked(model: ImageCollectionResponse) {
        val fragment = BooksCompilationFragment.getInstance(model.id.toInt())
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_frame, fragment)
            ?.commit()
    }
}