package com.example.allticks.screens.edit

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.allticks.R
import com.example.allticks.TASK_ID
import com.example.allticks.common.ext.idFromParameter
import com.example.allticks.common.snackbar.SnackbarManager
import com.example.allticks.model.Task
import com.example.allticks.model.service.StorageService
import com.example.allticks.screens.AllTicksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService,
) : AllTicksViewModel() {
  val task = mutableStateOf(Task())

  init {
    val taskId = savedStateHandle.get<String>(TASK_ID)
    if (taskId != null) {
      launchCatching {
        val t = storageService.getTask(taskId.idFromParameter()) ?: Task()
        task.value = t
        Log.i("TAG_EditTaskVM", t.toString())
      }
    }
  }

  fun onTitleChange(newValue: String) {
    if (newValue.isEmpty()) {
      SnackbarManager.showMessage(R.string.task_title_empty)
      return
    }
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
  fun onCategoryChange(newCategory: String) {
    task.value = task.value.copy(category = newCategory)
  }

  fun onDoneClick(popUpScreen: () -> Unit) {
    launchCatching {
      val editedTask = task.value
      if (editedTask.title.isEmpty()) {
        SnackbarManager.showMessage(R.string.task_title_empty)
        return@launchCatching
      }
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
