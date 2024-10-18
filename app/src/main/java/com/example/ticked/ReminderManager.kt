package com.example.ticked

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class ReminderManager(private val context: Context) {

    fun scheduleReminder(task: Task) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Calculate the time for the reminder
        val reminderTime = task.time // Use the time you set in the Task

        // Create an intent for the AlarmReceiver
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("task_name", task.name)
        }

        // Create a PendingIntent for the AlarmReceiver
        val pendingIntent = PendingIntent.getBroadcast(
            context, task.id.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Schedule the reminder
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent)
    }
}
