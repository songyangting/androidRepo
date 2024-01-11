package com.example.kotlinpractical10

import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    // implement viewmodel for the task list
    private val taskList = mutableListOf<String>()

    fun getTaskList(): List<String> {
        return taskList
    }

    fun setTask(task : String): Unit {
        taskList.add(0, task)
    }

}