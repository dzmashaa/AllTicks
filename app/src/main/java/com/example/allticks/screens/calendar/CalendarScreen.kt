package com.example.allticks.screens.calendar

import android.telecom.Call
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allticks.R
import com.example.allticks.screens.task.TasksViewModel
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel()
){
    CalendarScreenContent()
}

@Composable
fun CalendarScreenContent(){
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
    ){
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Text(text = stringResource(R.string.calendar),
            style = MaterialTheme.typography.titleLarge)
    }
}