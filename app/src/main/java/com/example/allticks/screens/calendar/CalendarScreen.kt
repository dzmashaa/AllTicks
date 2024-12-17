package com.example.allticks.screens.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allticks.R
import com.example.allticks.model.Task
import com.example.allticks.screens.task.TaskItem
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight
import com.example.allticks.ui.theme.secondaryLight
import com.example.allticks.ui.theme.textColor
import com.example.allticks.ui.theme.whiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    openScreen: (String) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
){
    val datePickerState = rememberDatePickerState()
    val tasksForSelectedDate by viewModel.tasksForSelectedDate

    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (datePickerState.selectedDateMillis == null) {
            viewModel.setSelectedDate(System.currentTimeMillis()) // Встановлення поточної дати, якщо дата не вибрана
        } else {
            viewModel.setSelectedDate(datePickerState.selectedDateMillis) // Встановлення вибраної дати
        }
    }
    CalendarScreenContent(
        datePickerState = datePickerState,
        tasksForSelectedDate = tasksForSelectedDate,
        onTaskCheckChange = viewModel::onTaskCheckChange,
        onAddClick = viewModel::onAddClick,
        openScreen = openScreen,
        onTaskClick = { task -> viewModel.onTaskClick(task, openScreen) }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreenContent(datePickerState: DatePickerState,
                          tasksForSelectedDate: List<Task>,
                          onAddClick: ((String) -> Unit) -> Unit,
                          onTaskCheckChange: (Task) -> Unit,
                          openScreen: (String) -> Unit,
                          onTaskClick: (Task) -> Unit){
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
                .fillMaxHeight()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(primaryLight, backgroundLight)
                    )
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Text(
                text = stringResource(R.string.calendar),
                style = MaterialTheme.typography.titleLarge
            )
            val customColors = lightColorScheme(
                primary = secondaryLight,
                onPrimary = whiteColor,
                surface = backgroundLight
            )
            val customTypography = Typography(
                bodyLarge = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 18.sp,
                    color = textColor,
                    letterSpacing = 0.5.sp
                )
            )
            MaterialTheme(colorScheme = customColors, typography = customTypography) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                        title = null,
                        headline = null,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (tasksForSelectedDate.isEmpty()) {
                    Text(
                        text = "No task for this day",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                } else {
                    tasksForSelectedDate.forEach { task ->
                        TaskItem(
                            task = task,
                            onCheckChange = { onTaskCheckChange(task) },
                            onTaskClick = { onTaskClick(task) }
                        )
                    }

                }
            }
        }
    }
}
