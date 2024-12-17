package com.example.allticks

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.allticks.common.navigationBar.BottomNavigationBar
import com.example.allticks.common.navigationBar.getBottomBarScreens
import com.example.allticks.common.snackbar.SnackbarManager
import com.example.allticks.screens.calendar.CalendarScreen
import com.example.allticks.screens.login.LoginScreen
import com.example.allticks.screens.profile.ProfileScreen
import com.example.allticks.screens.signup.SignUpScreen
import com.example.allticks.screens.splash.SplashScreen
import com.example.allticks.screens.task.TaskScreen
import com.example.allticks.screens.welcome.WelcomeScreen
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.primaryLight
import kotlinx.coroutines.CoroutineScope
import com.example.allticks.screens.edit.EditTaskScreen


@Composable
fun AllTicksApp() {
    AllTicksTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val appState = rememberAppState(snackbarHostState)
        val currentRoute = appState.navController.currentBackStackEntryAsState().value?.destination?.route

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(16.dp),
                    snackbar = { snackbarData ->
                        Snackbar(snackbarData,
                            containerColor = primaryLight,
                            shape = RoundedCornerShape(8.dp))
                    }
                )
            },
            bottomBar = {
                if (getBottomBarScreens().any { it.route == currentRoute }) {
                    BottomNavigationBar(navController = appState.navController,
                        appState = appState)
                }
            }
        ) { innerPaddingModifier ->
            NavHost(
                navController = appState.navController,
                startDestination = SPLASH_SCREEN,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                allTicksGraph(appState)
            }
        }
    }

}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(snackbarHostState, navController, snackbarManager, resources, coroutineScope) {
        AllTicksAppState(
            snackbarHostState,
            navController,
            snackbarManager,
            resources,
            coroutineScope
        )
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

fun NavGraphBuilder.allTicksGraph(appState: AllTicksAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(WELCOME_SCREEN) {
        WelcomeScreen(
            openScreen = { route -> appState.navigate(route) },
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(LOGIN_SCREEN) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(PROFILE_SCREEN) {
        ProfileScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) })
    }
    composable(TASKS_SCREEN) {
        TaskScreen(openScreen = { route -> appState.navigate(route) })
    }
    composable(CALENDAR_SCREEN) {
        CalendarScreen(openScreen = { route -> appState.navigate(route) })
    }
    composable(
        route = "$EDIT_TASK_SCREEN$TASK_ID_ARG&dueDate={dueDate}",
    arguments = listOf(navArgument(TASK_ID) {
        nullable = true
        defaultValue = null
    },
        navArgument("dueDate") {
            nullable = true
            defaultValue = null
        })
    ) {
        val dueDate = it.arguments?.getString("dueDate")
        EditTaskScreen(
            dueDate = dueDate,
            popUpScreen = { appState.popUp() }
        )
    }
}