package com.example.allticks.screens.task

import com.example.allticks.EDIT_TASK_SCREEN
import com.example.allticks.TASK_ID
import com.example.allticks.model.Task
import com.example.allticks.model.service.StorageService
import com.example.allticks.screens.AllTicksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val storageService: StorageService,
): AllTicksViewModel(){
    val tasks = storageService.tasks

    fun onTaskCheckChange(task: Task) {
        launchCatching { storageService.update(task.copy(completed = !task.completed)) }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

    fun onTaskClick(task: Task, openScreen: (String) -> Unit){
        launchCatching {
            openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
        }
    }

}