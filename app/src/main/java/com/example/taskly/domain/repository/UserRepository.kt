package com.example.taskly.domain.repository

import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.model.User

interface UserRepository {
    suspend fun saveUserProfile(name: String): Resource<Unit>

    suspend fun getCurrentUserProfile(): Resource<User>

    fun getCurrentUserId(): String?
}