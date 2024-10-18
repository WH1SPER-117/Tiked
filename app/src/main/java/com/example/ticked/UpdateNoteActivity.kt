package com.example.ticked

import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ticked.databinding.ActivityUpdateNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var notesManager: NotesManager
    private var noteId: Int = -1
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        notesManager = NotesManager(this)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()

        soundId = soundPool.load(this, R.raw.alarm, 1)

        val spinner = binding.editprioritySpinner
        val priorities = arrayOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priorities)
        spinner.adapter = adapter

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            val note = notesManager.getNoteByID(noteId)
            if (note != null) {
                binding.updatetitleEditText.setText(note.title)
                binding.updatecontentEditText.setText(note.content)
                val priorityPosition = priorities.indexOf(note.priority)
                if (priorityPosition >= 0) {
                    spinner.setSelection(priorityPosition)
                }
            } else {
                Toast.makeText(this@UpdateNoteActivity, "Note not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.updatesaveButton.setOnClickListener {
            val newTitle = binding.updatetitleEditText.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()
            val newPriority = spinner.selectedItem.toString()

            if (newTitle.isBlank() || newContent.isBlank()) {
                Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedNote = Note(noteId, newTitle, newContent, newPriority)

            CoroutineScope(Dispatchers.Main).launch {
                notesManager.updateNote(updatedNote)
                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                Toast.makeText(this@UpdateNoteActivity, "Changes Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}
