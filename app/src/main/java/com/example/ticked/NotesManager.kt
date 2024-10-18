package com.example.ticked

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesManager(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val noteDao = database.noteDao()

    // Method to retrieve a specific note by its ID
    suspend fun getNoteByID(noteId: Int): Note? {
        return withContext(Dispatchers.IO) {
            noteDao.getNoteById(noteId)
        }
    }

    // Method to update an existing note
    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.update(note)
        }
    }

    // Method to insert a new note
    suspend fun insertNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }

    // Method to delete a note
    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }

    // Method to retrieve all notes
    suspend fun getAllNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            noteDao.getAllNotes()
        }
    }

    // Method to retrieve high-priority tasks
    suspend fun getHighPriorityTasks(): List<Note> {
        return withContext(Dispatchers.IO) {
            noteDao.getNotesByPriority("High")
        }
    }
}
