package com.example.allticks.screens.task

import androidx.lifecycle.viewModelScope
import com.example.allticks.EDIT_TASK_SCREEN
import com.example.allticks.TASK_ID
import com.example.allticks.model.Task
import com.example.allticks.model.TaskPriority
import com.example.allticks.model.service.StorageService
import com.example.allticks.screens.AllTicksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val storageService: StorageService,
): AllTicksViewModel(){
    private val _priorityFilter = MutableStateFlow<TaskPriority?>(null)
    val priorityFilter: StateFlow<TaskPriority?> = _priorityFilter

    val tasks = storageService.tasks
        .combine(_priorityFilter) { tasks, filter ->
            tasks.filter { task ->
                filter == null || task.taskPriority == filter
            }.sortedWith(compareBy<Task> { it.completed }
                .thenByDescending { it.createdAt })
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onTaskCheckChange(task: Task) {
        launchCatching { storageService.update(task.copy(completed = !task.completed)) }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

    fun onTaskClick(task: Task, openScreen: (String) -> Unit){
        launchCatching {
            openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
        }
    }
    fun setPriorityFilter(priority: TaskPriority?) {
        _priorityFilter.value = priority
    }
}