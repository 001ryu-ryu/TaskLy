package com.example.taskly.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.model.User
import com.example.taskly.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _user = MutableStateFlow<Resource<User>?>(null)
    val user = _user.asStateFlow()

    fun saveUserProfile(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.saveUserProfile(name)
        }
    }

    fun getCurrentUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = Resource.Loading
            val user = userRepository.getCurrentUserProfile()
            _user.value = user
        }
    }

    fun getCurrentUserId(): String? {
       return userRepository.getCurrentUserId()
    }
}

























