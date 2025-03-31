package com.example.kernel.UI

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.kernel.R
import com.example.kernel.databinding.ActivitySignInUpBinding
import com.example.kernel.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {

    private lateinit var binding:ActivityWelcomeScreenBinding
    private val descriptions = listOf(
        "We're excited to help you book and manage your service appointments with ease.",
        "Get the latest updates and news instantly.",
        "Enjoy a seamless experience with our new features."
    )
    private var index = 0
    private val handler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textSwitcher.setFactory {
            TextView(this).apply {
                textSize = 16f
                setTextColor(resources.getColor(android.R.color.darker_gray, theme))
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            }
        }

        binding.textSwitcher.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        binding.textSwitcher.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)

        startTextSwitching()

        binding.btnLogin.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.tvCreate.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startTextSwitching() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                binding.textSwitcher.setText(descriptions[index])
                index = (index + 1) % descriptions.size
                handler.postDelayed(this, 3000) // Change text every 3 seconds
            }
        }, 0)
    }
}