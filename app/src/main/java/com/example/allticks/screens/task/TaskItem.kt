package com.example.allticks.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allticks.model.Task
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.ContentAlpha
import com.example.allticks.R
import com.example.allticks.common.ext.hasDueDate
import com.example.allticks.common.ext.hasDueTime
import com.example.allticks.ui.theme.textColor
import com.example.allticks.ui.theme.whiteColor
import java.lang.StringBuilder

@Composable
fun TaskItem(
    task: Task,
    onCheckChange: (Task) -> Unit,
    onTaskClick: (Task) -> Unit
){
    val priority = task.taskPriority
    Card(
        colors = CardDefaults.cardColors(
            containerColor = whiteColor),
        modifier = Modifier.padding(16.dp, 8.dp),
        onClick = {onTaskClick(task)}
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth() .padding(vertical = 6.dp),
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onCheckChange(task) },
                modifier = Modifier.padding(8.dp, 0.dp) .scale(1.5f)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center)
                Text(text = getDueDateAndTime(task),
                    fontSize = 12.sp,
                    color = textColor.copy(alpha = ContentAlpha.medium))
            }

            Icon(
                painter = painterResource(R.drawable.baseline_star_24),
                tint = priority.color,
                contentDescription = "Priority Icon",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

private fun getDueDateAndTime(task: Task): String {
    val stringBuilder = StringBuilder("")

    if (task.hasDueDate()) {
        stringBuilder.append(task.dueDate)
        stringBuilder.append(" ")
    }

    if (task.hasDueTime()) {
        stringBuilder.append("at ")
        stringBuilder.append(task.dueTime)
    }

    return stringBuilder.toString()
}


@Preview(showBackground = true)
@Composable

fun TaskItemPrewiew(){
    val task = Task(
        title = "Task title",
        flag = true,
        completed = true
    )
    TaskItem(
        task = task,
        onCheckChange = {},
        onTaskClick = {}
    )
}