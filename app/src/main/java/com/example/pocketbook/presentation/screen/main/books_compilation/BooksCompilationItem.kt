package com.example.pocketbook.presentation.screen.main.books_compilation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.pocketbook.R
import com.example.pocketbook.databinding.BooksCompilationScreenItemBinding
import com.example.pocketbook.presentation.screen.main.MainFragment

class BooksCompilationItem : Fragment() {
    companion object {
        const val BACKGROUND_IMAGE_URL = "backGroundUrl"
        const val BUTTON_TEXT = "buttonText"
        const val HEADER_TEXT = "headerText"
        const val IMAGE_ID = "imgId"
        const val TAB_TEXT = "tabText"

        fun getInstance(
            imageId: String?,
            tabText: String?,
            headerText: String?,
            buttonText: String?,
            backGroundImageUrl: String?
        ): BooksCompilationItem {
            val fragment = BooksCompilationItem()
            val bundle = Bundle()
            bundle.putString(IMAGE_ID, imageId)
            bundle.putString(TAB_TEXT, tabText)
            bundle.putString(HEADER_TEXT, headerText)
            bundle.putString(BUTTON_TEXT, buttonText)
            bundle.putString(BACKGROUND_IMAGE_URL, backGroundImageUrl)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: BooksCompilationScreenItemBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BooksCompilationScreenItemBinding.inflate(inflater, container, false)
        setFields()
        return binding.root
    }

    private fun setFields() {
        activity?.let {
            Glide.with(it).load(arguments?.getString(BACKGROUND_IMAGE_URL))
                .into(binding.imageActivityImg)
        }
        binding.imageActivityTextTag.text = arguments?.getString(HEADER_TEXT)
        binding.imageActivityText.text = arguments?.getString(TAB_TEXT)
        binding.imageScreenActionButton.text = arguments?.getString(BUTTON_TEXT)
        binding.imageScreenCloseBtn.setOnClickListener {
            val fragment = MainFragment.getInstance()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_frame, fragment)
                ?.commit()
        }

        val ofInt =
            ObjectAnimator.ofInt(
                binding.imageActivityProgressBar,
                "progress",
                0,
                100
            )
        ofInt.interpolator = AccelerateInterpolator()
        ofInt.duration = 5000L
        ofInt.start()
        ofInt.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {

            }
        })
    }

    private fun showToast(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
}
