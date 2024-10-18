package com.example.ticked

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAdapter(
    private val notes: MutableList<Note>, // Change to MutableList<Note>
    private val context: Context,
    private val notesManager: NotesManager,
    private val onTaskDeleted: (Note) -> Unit // Callback for task deletion
) : RecyclerView.Adapter<TaskAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title // Assuming Note has a title property
        holder.contentTextView.text = note.content // Assuming Note has a content property

        // Set click listeners for update and delete buttons
        holder.updateButton.setOnClickListener {
            val intent = Intent(context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id) // Pass the note ID
            }
            context.startActivity(intent) // Start the UpdateNoteActivity
        }

        holder.deleteButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                notesManager.deleteNote(note) // Delete the note
                notes.removeAt(position) // Remove from the list
                notifyItemRemoved(position) // Notify that an item was removed
                onTaskDeleted(note) // Notify the activity to delete the task
            }
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    // Method to update the notes list in the adapter
    fun setTasks(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}
