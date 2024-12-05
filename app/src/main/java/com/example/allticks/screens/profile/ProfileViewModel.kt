package com.example.allticks.screens.profile

import androidx.lifecycle.ViewModel
import com.example.allticks.LOGIN_SCREEN
import com.example.allticks.SIGN_UP_SCREEN
import com.example.allticks.SPLASH_SCREEN
import com.example.allticks.model.service.AccountService
import com.example.allticks.screens.AllTicksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService
): AllTicksViewModel(){
    val uiState = accountService.currentUser.map {
        ProfileUiState(it.isAnonymous)
    }

    fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

    fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.deleteAccount()
            restartApp(SPLASH_SCREEN)
        }
    }
}