package com.example.taskly.domain.repository

import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.model.Task

interface TaskRepository {
    suspend fun addTask(task: Task): Resource<Unit>
    fun getCurrentUserId(): String?
    suspend fun getAllTasks(): Resource<List<Task>>
}