package com.example.kernel.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kernel.Adapters.EventAdapter
import com.example.kernel.Components.EventData
import com.example.kernel.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment :  Fragment(R.layout.fragment_home)  {

    private lateinit var recyclerView: RecyclerView
    private var eventList = ArrayList<EventData>()
    private lateinit var eventAdapter: EventAdapter
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        recyclerView = view.findViewById(R.id.recyclerView)
        val overlappingIcon = view.findViewById<ImageView>(R.id.overlapping_icon)
        //swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        val database = FirebaseDatabase.getInstance().getReference("Events")
        progressBar.visibility = View.VISIBLE

        database.get().addOnSuccessListener { snack ->
            for(key in snack.children){
                Log.d("EventData", "${key.value}")
                val data = key.getValue(EventData::class.java)

                if (data != null) {
                    eventList.add(data)
                }
            }

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            eventAdapter = EventAdapter(requireContext(), eventList)
            recyclerView.adapter = eventAdapter

            progressBar.visibility = View.GONE

            eventAdapter.setOnItemClickListener(object : EventAdapter.onItemClickListener {
                override fun onItemClicking(position: Int) {
                    TODO()
                }
            })

            /*if (eventList.isEmpty()) {
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, EmptyEventFragment())
                fragmentTransaction.commit()
            }*/
        }

        /*database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded) return
                val EventList = ArrayList<EventData>()

                for (EventsSnapshot in snapshot.children) {
                    val data = EventsSnapshot.getValue(EventData::class.java)
                    if (data != null) {
                        EventList.add(data)
                    }
                }

                progressBar.visibility = View.GONE


                if (EventList.isEmpty() && isAdded) {
                    val fragmentManager = parentFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, EmptyEventFragment())
                    fragmentTransaction.commit()
                }
                else if (isAdded) eventAdapter.updateData(EventList)

            }

            override fun onCancelled(error: DatabaseError) {
                if (!isAdded) return
                Toast.makeText(
                    requireContext(),
                    "Failed to load bookmarks: ${error.message}",
                    Toast.LENGTH_SHORT

                ).show()
            }

        })*/
    }
}