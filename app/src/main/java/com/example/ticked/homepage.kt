package com.example.ticked

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class homepage : AppCompatActivity() {

    private lateinit var notesManager: NotesManager
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        Log.d("HomepageActivity", "Homepage activity created")
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        // Set the date in a TextView
        val currentDateTime =
            SimpleDateFormat("EEEE, MMM d yyyy", Locale.getDefault()).format(Date())
        findViewById<TextView>(R.id.textView10).text = currentDateTime

        // Initialize NotesManager
        notesManager = NotesManager(this)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.highPriorityTasksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get high-priority tasks and set adapter
        lifecycleScope.launch {
            val highPriorityNotes = withContext(Dispatchers.IO) {
                notesManager.getHighPriorityTasks()
            }
            notesAdapter = NotesAdapter(highPriorityNotes.toMutableList(), this@homepage, notesManager)
            recyclerView.adapter = notesAdapter
        }

        val btnNavigate: ImageView = findViewById(R.id.imageView15)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }

        // Bottom navigation setup
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.page_1
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> true
//                R.id.page_2 -> {
//                    startActivity(Intent(this, Calender::class.java))
//                    true
//                }
                R.id.page_3 -> {
                    startActivity(Intent(this, TasklistActivity::class.java)) // Start the TaskList activity
                    true
                }
                R.id.page_4 -> {
                    startActivity(Intent(this, Timer::class.java))
                    true
                }
                R.id.page_5 -> {
                    startActivity(Intent(this, profile::class.java))
                    true
                }
                else -> false
            }
        }
    }

    // Override onActivityResult to handle note updates
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == TasklistActivity.UPDATE_NOTE_REQUEST) {
            // Get updated note data and update the list
            val updatedNote = data?.getParcelableExtra<Note>("updated_note")
            updatedNote?.let { notesAdapter.updateNote(it) }
        }
    }
}
