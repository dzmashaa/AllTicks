package com.example.allticks.model

import com.example.allticks.ui.theme.highPriority
import com.example.allticks.ui.theme.lowPriority
import com.example.allticks.ui.theme.mediumPriority
import com.example.allticks.ui.theme.nonePriority

enum class TaskPriority(val label: String, val color: androidx.compose.ui.graphics.Color) {
    None("None", nonePriority),
    Low("Low", lowPriority),
    Medium("Medium", mediumPriority),
    High("High", highPriority);

    companion object {
        fun fromString(value: String): TaskPriority {
            return entries.find { it.label.equals(value, ignoreCase = true) } ?: None
        }
    }
}