package com.example.pocketbook.presentation.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.data.NetworkClient
import com.example.pocketbook.data.classes.profile.UserProfileDataClass
import com.example.pocketbook.databinding.ProfileScreenBinding
import com.example.pocketbook.presentation.screen.main.MainActivity.Companion.DATA_FAIL
import com.example.pocketbook.presentation.screen.profile.recycler_view.UserProfileAdapter
import com.example.pocketbook.presentation.screen.subscribe.SubscribeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserProfileFragment : Fragment() {

    companion object {
        fun getInstance(): UserProfileFragment {
            return UserProfileFragment()
        }

        const val BOOKS = "книги"
        const val ELAPSED_TIME = "Времени за чтением"
        const val MINUTES = "минут"
        const val PAGES = "стр"
        const val PAGES_PER_HOUR = "стр/час"
        const val READ = "Прочитано"
        const val READING_SPEED = "Скорость чтения"
    }

    private lateinit var binding: ProfileScreenBinding
    private var listOfMenu = mutableListOf<UserProfileDataClass>()
    private var imageUrlTop =
        "https://p1.hiclipart.com/preview/311/118/404/white-and-black-owl-painting-png-clipart.jpg"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        setClickListeners()
        loadFinishedBooks()
        initRecyclerViewData()
        initRecyclerView()
        return view
    }

    private fun setClickListeners() {
        binding.myProfileBuySubscribe.setOnClickListener {
            changeFragment(SubscribeFragment.getInstance())
        }
        binding.myProfileSettingsBtn.setOnClickListener {
            showToast("Not implemented")
        }
    }

    private fun changeFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_frame, fragment)?.commit()
    }

    private fun initRecyclerViewData() {
        addItem(0, READ, BOOKS, R.drawable.ic_launcher_background, "0")
        addItem(1, READ, PAGES, R.drawable.ic_launcher_background, "0")
        addItem(2, READING_SPEED, PAGES_PER_HOUR, R.drawable.ic_launcher_background, "0")
        addItem(3, ELAPSED_TIME, MINUTES, R.drawable.ic_launcher_background, "10")
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.myProfileRecyclerView.layoutManager = linearLayoutManager
        val adapter = UserProfileAdapter(context, listOfMenu)
        binding.myProfileRecyclerView.adapter = adapter
    }

    private fun addItem(
        categoryData: Int,
        bookTag: String,
        bookData: String,
        menuImage: Int,
        itemCount: String
    ) {
        ResourcesCompat.getDrawable(resources, menuImage, null)?.let {
            UserProfileDataClass(
                bookTag, bookData,
                it, itemCount
            )
        }?.let {
            listOfMenu.add(
                categoryData,
                it
            )
        }
    }

    private fun loadFinishedBooks() {
        NetworkClient.buildBookApiClient().getBooksCount().enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful && response.body() != null) {
                        val result: String? = response.body()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    showToast(DATA_FAIL)
                }

            }
        )
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        context?.let {
            Glide.with(it).load(imageUrlTop).into(binding.myProfileUserImage)
        }
    }
}