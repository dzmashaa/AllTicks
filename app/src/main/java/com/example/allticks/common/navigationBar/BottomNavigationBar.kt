package com.example.allticks.common.navigationBar

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.allticks.AllTicksAppState
import com.example.allticks.CALENDAR_SCREEN
import com.example.allticks.PROFILE_SCREEN
import com.example.allticks.R
import com.example.allticks.TASKS_SCREEN
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight
import com.example.allticks.ui.theme.secondaryLight
import com.example.allticks.ui.theme.textColor
import com.example.allticks.ui.theme.whiteColor


@Composable
fun getBottomBarScreens(): List<BottomBarItem> {
    return listOf(
        BottomBarItem(CALENDAR_SCREEN,  ImageVector.vectorResource(R.drawable.baseline_calendar_month_24), "Calendar"),
        BottomBarItem(TASKS_SCREEN, ImageVector.vectorResource(R.drawable.baseline_checklist_24), "Tasks"),
        BottomBarItem(PROFILE_SCREEN, Icons.Default.AccountCircle, "Profile")
    )
}

@Composable
fun BottomNavigationBar(navController: NavController, appState: AllTicksAppState) {
    val bottomBarScreens = getBottomBarScreens()
    val currentRoute = appState.navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(containerColor = primaryLight) {

        bottomBarScreens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(screen.icon,
                        contentDescription = screen.label)
                },
                label = {
                    Text(screen.label,
                        style = MaterialTheme.typography.labelSmall
                ) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        appState.navigateAndPopUp(screen.route, currentRoute ?: "")
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = backgroundLight,
                    unselectedIconColor = textColor,
                    selectedTextColor = backgroundLight,
                    unselectedTextColor = textColor,
                    indicatorColor = secondaryLight,

                )
            )
        }
    }
}


