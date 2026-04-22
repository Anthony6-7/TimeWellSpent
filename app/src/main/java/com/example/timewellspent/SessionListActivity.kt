package com.example.timewellspent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.timewellspent.databinding.ActivitySessionListBinding


class SessionListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySessionListBinding
    private lateinit var adapter: SessionAdapter
    private lateinit var sessionList: List<Session>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySessionListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // make backendless call to retrieve all data
        // in the handleResponse, get the list of data and constructor the adapter & apply to the reyclerview
        Backendless.Data.of<Session>(Session::class.java)
            .find(object : AsyncCallback<List<Session>> {
                override fun handleResponse(foundSessions: List<Session>) {
                    adapter = SessionAdapter(foundSessions)
                    binding.recyclerViewActivitySession.adapter = adapter
                    binding.recyclerViewActivitySession.layoutManager = LinearLayoutManager(this@SessionListActivity)

                }

                override fun handleFault(fault: BackendlessFault) {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                }
            })
    }

}