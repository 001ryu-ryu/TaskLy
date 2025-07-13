package com.example.taskly.data.repository

import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.authantication.utils.await
import com.example.taskly.data.model.Task
import com.example.taskly.domain.repository.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val database: FirebaseDatabase,
                                             private val auth: FirebaseAuth) : TaskRepository {
    override suspend fun addTask(task: Task): Resource<Unit> {
        val uid = auth.currentUser?.uid ?: return Resource.Failure(Exception("could not get the current user"))
        val taskId = database.reference.push().key ?: return Resource.Failure(Exception("Failed to generate task Id"))

        val taskWithId = task.copy(id = taskId)

        return try {
            database.reference.child("tasks")
                .child(uid)
                .child(taskId)
                .setValue(taskWithId)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getAllTasks(): Resource<List<Task>> {
        val uid = auth.currentUser?.uid ?: return Resource.Failure(Exception("User not logged in"))

        return try {
            val snapshot = database.reference
                .child("tasks")
                .child(uid)
                .get()
                .await()

            val taskList = snapshot.children.mapNotNull {
                it.getValue(Task::class.java)
            }

            Resource.Success(taskList)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}













