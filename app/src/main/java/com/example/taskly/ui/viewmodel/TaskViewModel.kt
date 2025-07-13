package com.example.taskly.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.model.Task
import com.example.taskly.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    private val _addTaskState = MutableStateFlow<Resource<Unit>?>(null)
    val addTaskState = _addTaskState.asStateFlow()

    private val _tasks = MutableStateFlow<Resource<List<Task>>?>(null)
    val tasks = _tasks.asStateFlow()

    fun addTask(task: Task) {
        val uid = taskRepository.getCurrentUserId()
        if (uid == null) {
            _addTaskState.value = Resource.Failure(Exception("User not logged in"))
            return // stop early no need to launch the coroutine if uid null
        }

        viewModelScope.launch(Dispatchers.IO) {
            _addTaskState.value = Resource.Loading
            _addTaskState.value = taskRepository.addTask(task)
        }
    }

    fun getAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _tasks.value = Resource.Loading
            _tasks.value = taskRepository.getAllTasks()
        }
    }
}



























