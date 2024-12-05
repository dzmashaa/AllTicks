package com.example.allticks.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.example.allticks.AllTicksAppState
import com.example.allticks.SPLASH_SCREEN
import com.example.allticks.TASKS_SCREEN
import com.example.allticks.WELCOME_SCREEN
import com.example.allticks.model.service.AccountService
import com.example.allticks.screens.AllTicksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
) : AllTicksViewModel()  {
    val showError = mutableStateOf(false)

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        if (accountService.hasUser) openAndPopUp(TASKS_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(WELCOME_SCREEN, SPLASH_SCREEN)
    }
}