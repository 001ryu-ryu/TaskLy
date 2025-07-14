package com.example.taskly.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.example.taskly.data.model.Task

object TaskNavType : NavType<Task>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Task? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): Task {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: Task) {
        bundle.putString(key, Json.encodeToString(value))
    }
}
