package com.example.taskly.data.repository

import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.authantication.utils.await
import com.example.taskly.data.model.Task
import com.example.taskly.domain.repository.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override suspend fun getAllTasks(): Flow<Resource<List<Task>>> = callbackFlow {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            trySend(Resource.Failure(Exception("User not logged in")))
            close()
            return@callbackFlow
        }

        val taskRef = database.reference.child("tasks").child(uid)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val taskList = snapshot.children.mapNotNull {
                    it.getValue(Task::class.java)
                }
                trySend(Resource.Success(taskList))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Failure(error.toException()))
            }
        }

        taskRef.addValueEventListener(listener)

        awaitClose {
            taskRef.removeEventListener(listener)
        }
    }

    override suspend fun editTaskName(taskId: String, taskName: String): Resource<Unit> {
        val uid = auth.currentUser?.uid ?: return Resource.Failure(Exception("could not get the current user"))

        return try {
            database.reference
                .child("tasks")
                .child(uid)
                .child(taskId)
                .child("title")
                .setValue(taskName)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun editTask(task: Task): Resource<Unit> {
        val uid = auth.currentUser?.uid ?: return Resource.Failure(Exception("could not get the current user"))
        val taskId = task.id
        if (taskId.isBlank()) return Resource.Failure(Exception("Task ID is missing"))
        return try {
            database.reference.child("tasks")
                .child(uid)
                .child(taskId)
                .setValue(task)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun deleteTask(task: Task): Resource<Unit> {
        val uid = auth.currentUser?.uid ?: return Resource.Failure(Exception("could not get the current user"))
        val taskId = task.id
        return try {
            database.reference.child("tasks")
                .child(uid)
                .child(taskId)
                .removeValue()
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}













