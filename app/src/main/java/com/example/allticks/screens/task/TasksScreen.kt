package com.example.allticks.screens.task

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.allticks.R
import com.example.allticks.model.Task
import com.example.allticks.model.TaskPriority
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight
import com.example.allticks.ui.theme.secondaryLight
import com.example.allticks.ui.theme.textColor

@Composable
fun TaskScreen(
    openScreen: (String) -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
){
    val tasks = viewModel
        .tasks
        .collectAsStateWithLifecycle(emptyList())
    val priorityFilter by viewModel.priorityFilter.collectAsStateWithLifecycle()
    TasksScreenContent(
        tasks = tasks.value,
        selectedPriority = priorityFilter,
        onAddClick = viewModel::onAddClick,
        onTaskCheckChange = viewModel::onTaskCheckChange,
        openScreen = openScreen,
        onTaskClick = { task -> viewModel.onTaskClick(task, openScreen) },
        onPrioritySelected = { priority -> viewModel.setPriorityFilter(priority) }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TasksScreenContent(
    tasks: List<Task>,
    selectedPriority: TaskPriority?,
    onAddClick: ((String) -> Unit) -> Unit,
    onTaskCheckChange: (Task) -> Unit,
    openScreen: (String) -> Unit,
    onTaskClick: (Task) -> Unit,
    onPrioritySelected: (TaskPriority?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick(openScreen) },
                containerColor = secondaryLight,
                contentColor = textColor,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(primaryLight, backgroundLight)
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Text(
                text = stringResource(R.string.tasks),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.filter_by),
                style = MaterialTheme.typography.labelSmall
            )
            Button(onClick = { expanded = !expanded },
                colors = ButtonDefaults.buttonColors(containerColor = backgroundLight)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(selectedPriority?.label ?: stringResource(id = R.string.priority))
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = stringResource(id = R.string.priority),
                        tint = textColor)
                }

            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false},
                modifier = Modifier
                    .background(backgroundLight)
                    .padding(16.dp, 8.dp)
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.all)) },
                    onClick = {
                        onPrioritySelected(null)
                        expanded = false
                    },
                    modifier = Modifier.background(backgroundLight),)

                TaskPriority.entries.forEach { priority ->
                    if (priority != TaskPriority.None) {
                        DropdownMenuItem(
                            text = { Text(priority.label) },
                            onClick = {
                                onPrioritySelected(priority)
                                expanded = false
                            })
                    }
                }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(tasks, key = { it.id }) { taskItem ->
                        TaskItem(
                            task = taskItem,
                            onCheckChange = { onTaskCheckChange(taskItem) },
                            onTaskClick = { onTaskClick(taskItem) }
                        )
                    }

                }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
    val task = Task(
        title = "Task title",
        flag = true,
        completed = true
    )

    AllTicksTheme {
        TasksScreenContent(
            tasks = listOf(task),
            onAddClick = { },
            onTaskCheckChange = { },
            openScreen = { },
            onTaskClick = {},
            onPrioritySelected = {},
            selectedPriority = null
        )
    }
}