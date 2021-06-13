package com.example.pocketbook.presentation.screen.my_books.my_shelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pocketbook.R
import com.example.pocketbook.databinding.MyBooksScreenShelfDefaultItemBinding

class DefaultShelfFragment : Fragment() {

    companion object {
        fun getInstance(): DefaultShelfFragment {
            return DefaultShelfFragment()
        }
    }

    private lateinit var binding: MyBooksScreenShelfDefaultItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyBooksScreenShelfDefaultItemBinding.inflate(inflater, container, false)
        val view = binding.root
        setClickListener()
        return view
    }

    private fun setClickListener() {
        binding.myBooksCreateShelf.setOnClickListener {
            changeFragment(MyShelfFragment.getInstance())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val replace: FragmentTransaction? =
            fragmentManager?.beginTransaction()?.replace(R.id.main_frame, fragment)
        replace?.addToBackStack(null)?.commit()
    }

}