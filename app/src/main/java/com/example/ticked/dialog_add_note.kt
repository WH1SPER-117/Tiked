package com.example.ticked

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class dialog_add_note : DialogFragment() {
    private var selectedDate: Long = System.currentTimeMillis() // Default to current date

    // Method to set the selected date from the Calender activity
    fun setSelectedDate(date: Long) {
        selectedDate = date
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.activity_dialog_add_note)

        val editTextTaskName: EditText = dialog.findViewById(R.id.taskEditText)
        val timePicker: TimePicker = dialog.findViewById(R.id.timePicker)
        val buttonSaveTask: Button = dialog.findViewById(R.id.buttonSaveTask)

        buttonSaveTask.setOnClickListener {
            val taskName = editTextTaskName.text.toString()

            // Ensure taskName is not empty
            if (taskName.isEmpty()) {
                editTextTaskName.error = "Task name is required"
                return@setOnClickListener
            }

            // Gather time from TimePicker
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, timePicker.hour)
                set(Calendar.MINUTE, timePicker.minute)
                set(Calendar.SECOND, 0)  // Set seconds to 0
                set(Calendar.MILLISECOND, 0)  // Set milliseconds to 0
            }

            val taskTime = calendar.timeInMillis
            val taskDate = System.currentTimeMillis()
            // Create task object
            val taskId = UUID.randomUUID().toString() // Generate a unique ID
            val task = Task(taskId, taskDate, taskTime, taskName)

            // Send task back to the activity
            //(activity as Calender).addTask(task)

            // Schedule the reminder
            val reminderManager = ReminderManager(requireContext())
            reminderManager.scheduleReminder(task)
            // Dismiss the dialog
            dialog.dismiss()
        }

        return dialog
    }
}
