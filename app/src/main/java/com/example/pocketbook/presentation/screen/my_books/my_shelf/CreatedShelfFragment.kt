package com.example.pocketbook.presentation.screen.my_books.my_shelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pocketbook.databinding.MyBooksScreenShelfCreatedItemBinding

class CreatedShelfFragment : Fragment() {

    companion object {
        fun getInstance(): CreatedShelfFragment {
            return CreatedShelfFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MyBooksScreenShelfCreatedItemBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}