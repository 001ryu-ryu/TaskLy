package com.example.taskly.data.authantication

sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val error: Exception) : Resource<Nothing>()
    object Loading: Resource<Nothing>()
}