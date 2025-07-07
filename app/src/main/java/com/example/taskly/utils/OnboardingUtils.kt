package com.example.taskly.utils

import android.content.Context
import androidx.core.content.edit

class OnboardingUtils(private val context: Context) {
    fun isOnboardingComplete(): Boolean {
        return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .getBoolean("completed", false)
    }

    fun setOnboardingComplete() {
        context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .edit {
                putBoolean("completed", true)
            }
    }
}