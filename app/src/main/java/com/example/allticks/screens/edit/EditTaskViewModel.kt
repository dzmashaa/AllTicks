package com.example.allticks.screens.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.allticks.TASK_ID
import com.example.allticks.common.ext.idFromParameter
import com.example.allticks.model.Task
import com.example.allticks.model.TaskPriority
import com.example.allticks.model.service.StorageService
import com.example.allticks.screens.AllTicksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService,
) : AllTicksViewModel() {
    val task = mutableStateOf(Task())

    init {
        val taskId = savedStateHandle.get<String>(TASK_ID)
        if (taskId != null) {
            launchCatching {
                task.value = storageService.getTask(taskId.idFromParameter()) ?: Task()
            }
        }
    }

    fun onTitleChange(newValue: String) {
        task.value = task.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        task.value = task.value.copy(description = newValue)
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        task.value = task.value.copy(dueDate = newDueDate)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        task.value = task.value.copy(dueTime = newDueTime)
    }

    fun onPriorityChange(priority: String) {
        task.value = task.value.copy(priority = priority)
    }

    fun onDoneClick(popUpScreen: () -> Unit) {
        launchCatching {
            val editedTask = task.value
            if (editedTask.id.isBlank()) {
                storageService.save(editedTask)
            } else {
                storageService.update(editedTask)
            }
            popUpScreen()
        }
    }

    fun onDeleteTaskClick(popUpScreen: () -> Unit) {
        launchCatching {
            storageService.delete(task.value.id)
            popUpScreen()
        }
    }
    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }
}