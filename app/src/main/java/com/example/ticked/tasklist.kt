package com.example.ticked

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasklistActivity : AppCompatActivity() {

    companion object {
        const val UPDATE_NOTE_REQUEST = 2 // Define the request code here
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter // Make sure this points to your TaskAdapter
    private lateinit var notesManager: NotesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasklist) // Ensure this layout exists

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.notesRecyclerView) // Ensure this ID exists in your layout
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize NotesManager
        notesManager = NotesManager(this)

        // Load tasks and set adapter
        loadTasks()

        val addactivity: Button = findViewById(R.id.addButton)
        addactivity.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadTasks() {
        CoroutineScope(Dispatchers.Main).launch {
            val tasks = withContext(Dispatchers.IO) {
                notesManager.getAllNotes() // Call the suspend function inside a coroutine
            }
            taskAdapter = TaskAdapter(tasks.toMutableList(), this@TasklistActivity, notesManager) { note ->
                // Handle task deletion logic here
                deleteTask(note)
            }
            recyclerView.adapter = taskAdapter
        }
    }

    private fun deleteTask(note: Note) {
        // Implement your deletion logic here
    }



}
