package com.example.ticked

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("TaskPreferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addTask(task: Task) {
        val tasks = getTasks().toMutableList()
        tasks.add(task)
        saveTasks(tasks)
    }

    fun getTasksForDate(date: Long): List<Task> {
        return getTasks().filter { it.date == date }
    }

    fun deleteTask(task: Task) {
        val tasks = getTasks().toMutableList()
        tasks.remove(task)
        saveTasks(tasks)
    }

    private fun getTasks(): List<Task> {
        val tasksJson = sharedPreferences.getString("tasks", null) ?: return emptyList()
        val type = object : TypeToken<List<Task>>() {}.type
        return gson.fromJson(tasksJson, type)
    }

    private fun saveTasks(tasks: List<Task>) {
        val json = gson.toJson(tasks)
        sharedPreferences.edit().putString("tasks", json).apply()
    }
}
