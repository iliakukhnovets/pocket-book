package com.example.pocketbook.presentation.screen.subscribe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pocketbook.R
import com.example.pocketbook.databinding.SubscribeScreenBinding
import com.example.pocketbook.presentation.screen.my_books.MyBooksFragment

class SubscribeFragment : Fragment() {

    companion object {
        fun getInstance(): SubscribeFragment {
            return com.example.pocketbook.presentation.screen.subscribe.SubscribeFragment()
        }
    }

    private lateinit var binding: SubscribeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SubscribeScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        setClickListeners()
        return view
    }

    private fun setClickListeners() {
        binding.subscribeScreenToolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_frame, MyBooksFragment.getInstance())?.commit()
        }
    }
}