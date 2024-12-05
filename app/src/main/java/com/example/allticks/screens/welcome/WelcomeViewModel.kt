package com.example.allticks.screens.welcome

import com.example.allticks.LOGIN_SCREEN
import com.example.allticks.PROFILE_SCREEN
import com.example.allticks.SIGN_UP_SCREEN
import com.example.allticks.TASKS_SCREEN
import com.example.allticks.WELCOME_SCREEN
import com.example.allticks.model.service.AccountService
import com.example.allticks.screens.AllTicksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val accountService: AccountService,): AllTicksViewModel() {
    fun onSignUpClick(navigate: (String) -> Unit){
        launchCatching {
            navigate(SIGN_UP_SCREEN)
        }
    }

    fun onLogInClick(navigate: (String) -> Unit){
        launchCatching {
            navigate(LOGIN_SCREEN)
        }
    }
    fun onAnonymAuthClick(openAndPopUp: (String, String) -> Unit){
        launchCatching {
            accountService.createAnonymousAccount()
            openAndPopUp(TASKS_SCREEN, WELCOME_SCREEN)
        }
    }
}