package com.example.taskly.data.repository

import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.authantication.utils.await
import com.example.taskly.data.model.User
import com.example.taskly.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : UserRepository {
    override suspend fun saveUserProfile(name: String): Resource<Unit> {
        val currentUser = firebaseAuth.currentUser

        val uid = currentUser?.uid ?: return Resource.Failure(Exception("User not logged in"))
        val email = currentUser.email ?: ""
        val user = User(uid = uid, name = name, email = email)

        return try {
            firebaseDatabase.reference
                .child("users")
                .child(uid)
                .setValue(user)
                .await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getCurrentUserProfile(): Resource<User> {
        val uid = firebaseAuth.currentUser?.uid ?: return Resource.Failure(Exception("User not logged in"))

        return try {
            val snapshot = firebaseDatabase.reference
                .child("users")
                .child(uid)
                .get()
                .await()

            val user = snapshot.getValue(User::class.java)
                ?: return Resource.Failure(Exception("User not found"))

            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

}












