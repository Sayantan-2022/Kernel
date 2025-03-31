package com.example.kernel.UI

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

class WelcomeScreen : AppCompatActivity() {

    private lateinit var textSwitcher: TextSwitcher
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

        textSwitcher = findViewById(R.id.textSwitcher)
        textSwitcher.setFactory {
            TextView(this).apply {
                textSize = 16f
                setTextColor(resources.getColor(android.R.color.darker_gray, theme))
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            }
        }

        textSwitcher.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        textSwitcher.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)

        startTextSwitching()
    }

    private fun startTextSwitching() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                textSwitcher.setText(descriptions[index])
                index = (index + 1) % descriptions.size
                handler.postDelayed(this, 3000) // Change text every 3 seconds
            }
        }, 0)
    }
}