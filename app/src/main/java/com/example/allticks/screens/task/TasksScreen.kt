package com.example.allticks.screens.task

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allticks.R
import com.example.allticks.model.Task
import com.example.allticks.model.TaskPriority
import com.example.allticks.screens.profile.ProfileViewModel
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight
import com.example.allticks.ui.theme.secondaryLight
import com.example.allticks.ui.theme.textColor

@Composable
fun TaskScreen(
    openScreen: (String) -> Unit,
    viewModel: TasksViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
  val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())
  val priorityFilter by viewModel.priorityFilter.collectAsStateWithLifecycle()
  val categoryFilter by viewModel.categoryFilter.collectAsStateWithLifecycle()
  val categories by profileViewModel.categories.collectAsState()
  TasksScreenContent(
      tasks = tasks.value,
      categories = categories,
      selectedPriority = priorityFilter,
      selectedCategory = categoryFilter,
      onAddClick = viewModel::onAddClick,
      onTaskCheckChange = viewModel::onTaskCheckChange,
      openScreen = openScreen,
      onTaskClick = { task -> viewModel.onTaskClick(task, openScreen) },
      onPrioritySelected = { priority -> viewModel.setPriorityFilter(priority) },
      onCategorySelected = { category -> viewModel.setCategoryFilter(category) })
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TasksScreenContent(
    tasks: List<Task>,
    categories: List<String>,
    selectedPriority: TaskPriority?,
    selectedCategory: String?,
    onAddClick: ((String) -> Unit) -> Unit,
    onTaskCheckChange: (Task) -> Unit,
    openScreen: (String) -> Unit,
    onTaskClick: (Task) -> Unit,
    onPrioritySelected: (TaskPriority?) -> Unit,
    onCategorySelected: (String?) -> Unit
) {
  var expandedPriority by remember { mutableStateOf(false) }
  var expandedCategory by remember { mutableStateOf(false) }
  Scaffold(
      floatingActionButton = {
        FloatingActionButton(
            onClick = { onAddClick(openScreen) },
            containerColor = secondaryLight,
            contentColor = textColor,
            modifier = Modifier.padding(16.dp)) {
              Icon(Icons.Filled.Add, "Add")
            }
      }) {
        Column(
            modifier =
                Modifier.fillMaxWidth()
                    .background(
                        brush =
                            Brush.linearGradient(colors = listOf(primaryLight, backgroundLight))),
            horizontalAlignment = Alignment.CenterHorizontally) {
              Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
              Text(
                  text = stringResource(R.string.tasks),
                  style = MaterialTheme.typography.titleLarge)
              Text(
                  text = stringResource(id = R.string.filter_by),
                  style = MaterialTheme.typography.labelSmall)
              Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceAround) {
                    Box {
                      Button(
                          onClick = { expandedPriority = !expandedPriority },
                          modifier = Modifier.width(140.dp),
                          colors = ButtonDefaults.buttonColors(containerColor = backgroundLight)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                              Text(
                                  selectedPriority?.label ?: stringResource(id = R.string.priority))
                              Icon(
                                  painter =
                                      painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                                  contentDescription = stringResource(id = R.string.priority),
                                  tint = textColor)
                            }
                          }
                      DropdownMenu(
                          expanded = expandedPriority,
                          onDismissRequest = { expandedPriority = false },
                          modifier = Modifier.background(backgroundLight).padding(16.dp, 8.dp)) {
                            DropdownMenuItem(
                                text = { Text(stringResource(id = R.string.all)) },
                                onClick = {
                                  onPrioritySelected(null)
                                  expandedPriority = false
                                },
                                modifier = Modifier.background(backgroundLight),
                            )

                            TaskPriority.entries.forEach { priority ->
                              DropdownMenuItem(
                                  text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween) {
                                          Text(text = priority.label)
                                          Spacer(modifier = Modifier.width(8.dp))
                                          Icon(
                                              painter =
                                                  painterResource(id = R.drawable.baseline_star_24),
                                              contentDescription =
                                                  stringResource(id = R.string.priority),
                                              tint = priority.color)
                                        }
                                  },
                                  onClick = {
                                    onPrioritySelected(priority)
                                    expandedPriority = false
                                  })
                            }
                          }
                    }
                    Box {
                      Button(
                          onClick = { expandedCategory = !expandedCategory },
                          modifier = Modifier.width(140.dp),
                          colors = ButtonDefaults.buttonColors(containerColor = backgroundLight)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                              Text(selectedCategory ?: stringResource(id = R.string.category))
                              Icon(
                                  painter =
                                      painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                                  contentDescription = stringResource(id = R.string.priority),
                                  tint = textColor)
                            }
                            DropdownMenu(
                                expanded = expandedCategory,
                                onDismissRequest = { expandedCategory = false },
                                modifier =
                                    Modifier.background(backgroundLight).padding(16.dp, 8.dp)) {
                                  DropdownMenuItem(
                                      text = { Text(stringResource(id = R.string.all)) },
                                      onClick = {
                                        onCategorySelected(null)
                                        expandedCategory = false
                                      },
                                      modifier = Modifier.background(backgroundLight),
                                  )
                                  categories.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(text = category) },
                                        onClick = {
                                          onCategorySelected(category)
                                          expandedCategory = false
                                        })
                                  }
                                }
                          }
                    }
                  }
              LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(tasks, key = { it.id }) { taskItem ->
                  TaskItem(
                      task = taskItem,
                      onCheckChange = { onTaskCheckChange(taskItem) },
                      onTaskClick = { onTaskClick(taskItem) })
                }
              }
            }
      }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
  val task = Task(title = "Task title", completed = true)
  val categories = listOf("Personal", "Work")
  AllTicksTheme {
    TasksScreenContent(
        tasks = listOf(task),
        onAddClick = {},
        onTaskCheckChange = {},
        openScreen = {},
        onTaskClick = {},
        onPrioritySelected = {},
        selectedPriority = null,
        categories = categories,
        selectedCategory = null,
        onCategorySelected = {})
  }
}
