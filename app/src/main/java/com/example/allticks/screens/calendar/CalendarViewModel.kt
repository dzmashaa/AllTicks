package com.example.allticks.screens.calendar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.allticks.EDIT_TASK_SCREEN
import com.example.allticks.TASK_ID
import com.example.allticks.model.Task
import com.example.allticks.model.service.StorageService
import com.example.allticks.screens.AllTicksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CalendarViewModel @Inject constructor(private val storageService: StorageService) :
    AllTicksViewModel() {
  private val _selectedDate = mutableStateOf<String?>(null)
  private val _tasksForSelectedDate = mutableStateOf<List<Task>>(emptyList())
  val tasksForSelectedDate: State<List<Task>> = _tasksForSelectedDate
  init {
    setSelectedDate(System.currentTimeMillis())
  }

  fun onAddClick(openScreen: (String) -> Unit) {
    val dateArg = _selectedDate.value ?: ""
    openScreen("$EDIT_TASK_SCREEN?dueDate=$dateArg")
  }

  fun setSelectedDate(date: Long?) {
    val selectedDateMillis = date ?: System.currentTimeMillis()
    _selectedDate.value = convertMillisToDate(selectedDateMillis)
    loadTasksForSelectedDate(_selectedDate.value)
  }
  fun onTaskClick(task: Task, openScreen: (String) -> Unit) {
    launchCatching { openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}") }
  }
  fun onTaskCheckChange(task: Task) {
    launchCatching {
      val updatedTask = task.copy(completed = !task.completed)
      storageService.update(updatedTask)
      loadTasksForSelectedDate(_selectedDate.value)
    }
  }

  private fun convertStringToDate(dateString: String): Date? {
    if (dateString.isEmpty()) return null
    val formatter = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
    return formatter.parse(dateString)?.takeIf { formatter.format(it) == dateString }
  }

  private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
  }

  private fun convertDateToMillis(dateString: String): Long {
    return convertStringToDate(dateString)?.time ?: 0L
  }

  private fun loadTasksForSelectedDate(date: String?) {
    viewModelScope.launch {
      storageService.tasks.collect { allTasks ->
        val selectedDateMillis = date?.let { convertDateToMillis(it) } ?: 0L
        _tasksForSelectedDate.value =
            allTasks.filter {
              val taskDateMillis = convertDateToMillis(it.dueDate)
              taskDateMillis == selectedDateMillis
            }
      }
    }
  }
}
