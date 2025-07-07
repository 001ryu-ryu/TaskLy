package com.example.taskly.data.authantication.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.resume

suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine {
        addOnCompleteListener { task ->
            if (task.exception != null) {
                it.resumeWithException(task.exception!!)
            } else {
                it.resume(task.result)
            }
        }
    }
}