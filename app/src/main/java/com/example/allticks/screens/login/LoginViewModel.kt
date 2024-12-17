package com.example.allticks.screens.login

import androidx.compose.runtime.mutableStateOf
import com.example.allticks.LOGIN_SCREEN
import com.example.allticks.PROFILE_SCREEN
import com.example.allticks.SIGN_UP_SCREEN
import com.example.allticks.R
import com.example.allticks.TASKS_SCREEN
import com.example.allticks.common.ext.isValidEmail
import com.example.allticks.common.snackbar.SnackbarManager
import com.example.allticks.model.service.AccountService
import com.example.allticks.screens.AllTicksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
): AllTicksViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onLogInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_password_error)
            return
        }
        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(TASKS_SCREEN, LOGIN_SCREEN)
        }
    }
    fun onSignUpClick(openAndPopUp: (String, String) -> Unit){
        launchCatching {
            openAndPopUp(SIGN_UP_SCREEN, LOGIN_SCREEN)
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(R.string.recovery_email_sent)
        }
    }
}