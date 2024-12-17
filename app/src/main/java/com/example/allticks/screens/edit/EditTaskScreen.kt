package com.example.allticks.screens.edit

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allticks.R
import com.example.allticks.common.composable.BasicButton
import com.example.allticks.common.composable.BasicCard
import com.example.allticks.common.composable.BasicField
import com.example.allticks.common.composable.CancelButton
import com.example.allticks.common.composable.ConfirmButton
import com.example.allticks.model.Task
import com.example.allticks.model.TaskPriority
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.buttonLightColor
import com.example.allticks.ui.theme.primaryLight
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun EditTaskScreen(
    dueDate: String?,
    popUpScreen: () -> Unit,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    LaunchedEffect(dueDate) {
        if (dueDate != null && viewModel.task.value.id.isBlank()) {
            SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH).parse(dueDate)
                ?.let { viewModel.onDateChange(it.time) }
        }
    }
    val task by viewModel.task
    val activity = LocalContext.current as AppCompatActivity
    EditTaskScreenContent(
        task = task,
        isNewTask = task.id.isBlank(),
        onDoneClick = { viewModel.onDoneClick(popUpScreen) },
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onDeleteClick = { viewModel.onDeleteTaskClick(popUpScreen) },
        onPriorityChange = viewModel::onPriorityChange,
        activity = activity
    )
}

@Composable
fun EditTaskScreenContent(
    modifier: Modifier = Modifier,
    task: Task,
    isNewTask: Boolean,
    onDoneClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onDeleteClick:() ->Unit,
    onPriorityChange: (String) -> Unit,
    activity: AppCompatActivity?
) {
    var selectedPriority by remember(task.taskPriority) { mutableStateOf(task.taskPriority) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(
                rememberScrollState()
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(primaryLight, backgroundLight)
                )
            )
            .padding(16.dp, 8.dp)
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.edit_task),
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Text(
            text = stringResource(R.string.title),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 30.dp)
        )
        BasicField(R.string.title, task.title, onTitleChange)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 24.dp)
        )
        BasicField(R.string.description, task.description, onDescriptionChange)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        CardEditors(task, onDateChange, onTimeChange, activity)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        var expanded by remember { mutableStateOf(false) }
        Text(
            text = stringResource(R.string.priority),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 24.dp)
        )
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp)) {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = backgroundLight, contentColor = buttonLightColor ),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = selectedPriority.label)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                        contentDescription = stringResource(id = R.string.priority),
                        tint = selectedPriority.color)
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(backgroundLight),
            ) {
                TaskPriority.entries.forEach { priority ->
                    DropdownMenuItem(
                        onClick = {
                            selectedPriority = priority
                            onPriorityChange(priority.label)
                            expanded = false
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = 4.dp,
                                vertical = 4.dp)
                            .background(backgroundLight),
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = priority.label)
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                                    contentDescription = stringResource(id = R.string.priority),
                                    tint = priority.color)
                            }
                        }
                    )
                }
            }
        }

        if(isNewTask){
            BasicButton(text = R.string.create) { onDoneClick() }
        }
        else{
            Row( modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                CancelButton(text = R.string.delete) {
                   onDeleteClick()
                }
                Spacer(modifier = Modifier.width(16.dp))
                ConfirmButton(text = R.string.save) {
                    onDoneClick()
                }
            }
        }
    }

}

@Composable
private fun CardEditors(
    task: Task,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    activity: AppCompatActivity?
) {
    BasicCard(
        title = R.string.date,
        icon = R.drawable.baseline_calendar_month_24,
        content = task.dueDate,
        onClick = { showDatePicker(activity, onDateChange) })
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
    BasicCard(
        title = R.string.time,
        icon = R.drawable.baseline_access_time_24,
        content = task.dueTime,
        onClick = { showTimePicker(activity, onTimeChange) })
}

private fun showDatePicker(activity: AppCompatActivity?, onDateChange: (Long) -> Unit) {
    val picker = MaterialDatePicker.Builder.datePicker().build()

    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { timeInMillis -> onDateChange(timeInMillis) }
    }
}

private fun showTimePicker(activity: AppCompatActivity?, onTimeChange: (Int, Int) -> Unit) {
    val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { onTimeChange(picker.hour, picker.minute) }
    }
}

@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview() {
    val task = Task(
        title = "Task title",
        description = "Task description",
        flag = true
    )

    AllTicksTheme {
        EditTaskScreenContent(
            task = task,
            isNewTask = false,
            onDoneClick = { },
            onTitleChange = { },
            onDescriptionChange = { },
            onDateChange = { },
            onDeleteClick = { },
            onTimeChange = { _, _ -> },
            onPriorityChange = { },
            activity = null
        )
    }
}
