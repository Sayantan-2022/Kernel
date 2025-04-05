package com.example.kernel.UI

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.kernel.R
import org.json.JSONObject

class GoogleFormActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        webView = findViewById(R.id.webView)

        val eventName = intent.getStringExtra("eventName")
        val eventDate = intent.getStringExtra("eventDate")
        val eventTime = intent.getStringExtra("eventTime")

        if (eventName != null) {
            if (eventDate != null) {
                if (eventTime != null) {
                    createGoogleForm(eventName, eventDate, eventTime, this) { formUrl ->
                        if (formUrl != null) {
                            webView.webViewClient = WebViewClient()
                            webView.apply {
                                settings.javaScriptEnabled = true
                                loadUrl(formUrl.toString())
                            }
                        } else {
                            Log.e("GoogleForm", "Failed to create form")
                        }
                    }
                }
            }
        }
    }

    private fun createGoogleForm(eventName: String, eventDate: String, eventTime: String, context: Context, callback: (String?) -> Unit) {
        val url = "https://script.google.com/macros/s/AKfycbw98DwgJk0a074KhPWOT__Cut7YieBTOQyN0LuFQ7lR3eI5EcVXHH-d7BXPzF4fANgLJw/exec"
        val requestBody = JSONObject()
        requestBody.put("eventName", eventName)
        requestBody.put("eventDate", eventDate)
        requestBody.put("eventTime", eventTime)

        val request = JsonObjectRequest(
            Request.Method.POST, url, requestBody,
            { response ->
                val formUrl = response.getString("formUrl")
                callback(formUrl)
            },
            { _ ->
                callback(null)
            }
        )

        Volley.newRequestQueue(context).add(request)
    }

}