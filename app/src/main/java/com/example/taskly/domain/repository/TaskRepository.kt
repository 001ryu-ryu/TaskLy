package com.example.taskly.domain.repository

import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task): Resource<Unit>
    fun getCurrentUserId(): String?
    suspend fun getAllTasks(): Flow<Resource<List<Task>>>
    suspend fun editTaskName(taskId: String, taskName: String): Resource<Unit>
    suspend fun editTask(task: Task): Resource<Unit>
    suspend fun deleteTask(task: Task): Resource<Unit>
}