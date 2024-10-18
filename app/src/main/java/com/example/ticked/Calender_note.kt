package com.example.ticked

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.os.Bundle
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Calender_note : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage) // Make sure this is the correct layout

        // Assume calendarView and addNoteButton are initialized here
    }

    private fun Calender_note(date: Long) {
        val dialogView = layoutInflater.inflate(R.layout.activity_calender_note, null)
        val noteEditText = dialogView.findViewById<EditText>(R.id.noteEditText)
        val timePicker = dialogView.findViewById<TimePicker>(R.id.timePicker)

        AlertDialog.Builder(this)
            .setTitle("Add Note")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val note = noteEditText.text.toString()
                val hour = timePicker.hour
                val minute = timePicker.minute
                saveNote(date, note, hour, minute)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveNote(date: Long, note: String, hour: Int, minute: Int) {
        // Save note to local storage or database
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            putExtra("note", note)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}
