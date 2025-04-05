package com.example.kernel.UI

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import com.example.kernel.R
import com.example.kernel.UI.Fragments.ChatAdapter
import com.example.kernel.Components.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LiveChatActivity : AppCompatActivity() {
    private lateinit var adapter: ChatAdapter
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton

    private lateinit var eventId: String
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: "Anonymous"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_chat)

        eventId = intent.getStringExtra("eventId") ?: return

        chatRecyclerView = findViewById(R.id.recyclerViewChat)
        messageEditText = findViewById(R.id.editTextMessage)
        sendButton = findViewById(R.id.buttonSend)

        adapter = ChatAdapter(currentUserId)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = adapter

        sendButton.setOnClickListener {
            val text = messageEditText.text.toString().trim()
            if (text.isNotEmpty()) {
                sendMessage(text)
                messageEditText.setText("")
            }
        }

        listenForMessages()
    }


    private fun sendMessage(text: String) {
        val message = ChatMessage(currentUserId, text,Timestamp.now())
        Firebase.firestore.collection("events")
            .document(eventId)
            .collection("chats")
            .add(message)

    }

    private fun listenForMessages() {
        Firebase.firestore.collection("events")
            .document(eventId)
            .collection("chats")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                val messages = snapshot?.toObjects(ChatMessage::class.java)
                messages?.let {
                    adapter.submitList(it)
                    chatRecyclerView.scrollToPosition(it.size - 1)
                }
            }
    }


    private fun fetchsentiment(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://senti-api-6.onrender.com/analyze/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}
