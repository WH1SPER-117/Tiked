package com.example.ticked

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val notes: MutableList<Note>,
    private val context: Context,
    private val notesManager: NotesManager
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    // ViewHolder class that holds the views
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        fun bind(note: Note) {
            titleTextView.text = note.title
            contentTextView.text = note.content

            // Set click listeners
            updateButton.setOnClickListener {
                // Handle update action here
                // You can launch an UpdateNoteActivity or perform another action
            }
            deleteButton.setOnClickListener {
                // Handle delete action here
                // Call notesManager.deleteNote and refresh the list
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    // Method to update the notes list in the adapter
    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    // Method to update a single note
    fun updateNote(updatedNote: Note) {
        val index = notes.indexOfFirst { it.id == updatedNote.id }
        if (index != -1) {
            notes[index] = updatedNote
            notifyItemChanged(index)
        }
    }
}
