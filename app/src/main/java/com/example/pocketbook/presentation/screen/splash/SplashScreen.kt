package com.example.pocketbook.presentation.screen.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketbook.databinding.SplashScreenBinding
import com.example.pocketbook.presentation.screen.main.MainActivity
import java.lang.ref.WeakReference


class SplashScreen : AppCompatActivity() {
    companion object {
        private const val START_TIMER = 4000L
    }

    private lateinit var handler: Handler
    private lateinit var binding: SplashScreenBinding

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = SplashScreenBinding.inflate(layoutInflater)
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(MemoryLeak(this), START_TIMER)
        setContentView(binding.root)
    }

    private class MemoryLeak(context: Context) : Runnable {
        private val weakReference: WeakReference<Context> = WeakReference(context)

        override fun run() {
            val context: Context? = weakReference.get()
            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        }
    }
}
