package com.example.pocketbook.presentation.screen.main.tabs.audio_book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pocketbook.databinding.MainScreenAudioBookTabBinding

class AudioBookPageFragment : Fragment() {
    companion object {
        fun getInstance(): AudioBookPageFragment {
            return com.example.pocketbook.presentation.screen.main.tabs.audio_book.AudioBookPageFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainScreenAudioBookTabBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}