package com.example.allticks

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allticks.screens.auth.AuthorizationScreen
import com.example.allticks.pages.ProfileScreen
import com.example.allticks.pages.WelcomeScreen

enum class AllTicksScreen(@StringRes val title: Int){
    Start(title = R.string.app_name),
    LogIn(title = R.string.login),
    SingUp(title = R.string.sing_up),
    Profile(title = R.string.profile)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllTicksApp(authViewModel: AuthViewModel = viewModel(), navController: NavHostController = rememberNavController(), modifier: Modifier = Modifier){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AllTicksScreen.valueOf(
        backStackEntry?.destination?.route ?: AllTicksScreen.Start.name
    )
    val authState = authViewModel.authState.observeAsState()

    val context = LocalContext.current
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(AllTicksScreen.Profile.name)
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }

    }
    Scaffold {
            innerPadding ->
        NavHost(navController = navController, startDestination = AllTicksScreen.Start.name){
            composable(route = AllTicksScreen.Start.name){
                WelcomeScreen(
                    onSingUpButtonClicked =  {
                        navController.navigate(AllTicksScreen.SingUp.name)
                    },
                    onLogInButtonClicked = {
                        navController.navigate(AllTicksScreen.LogIn.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = AllTicksScreen.SingUp.name){
                AuthorizationScreen(
                    true,
                    onProposalButtonClicked =  {
                        navController.navigate(AllTicksScreen.LogIn.name)
                    },
                    onButtonClicked = {email, password ->
                        authViewModel.signup(email, password)
                    },
                )
            }
            composable(route = AllTicksScreen.LogIn.name){
                AuthorizationScreen(
                    false,
                    onProposalButtonClicked =  {
                        navController.navigate(AllTicksScreen.SingUp.name)
                    },
                    onButtonClicked = {email, password ->
                        authViewModel.login(email, password)
                    },
                )
            }
            composable("profile"){
                ProfileScreen(navController = navController, authViewModel = authViewModel )
            }
        }
    }

}