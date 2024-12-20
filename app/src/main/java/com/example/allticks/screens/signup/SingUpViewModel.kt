package com.example.allticks.screens.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.allticks.LOGIN_SCREEN
import com.example.allticks.R
import com.example.allticks.SIGN_UP_SCREEN
import com.example.allticks.TASKS_SCREEN
import com.example.allticks.common.ext.isValidEmail
import com.example.allticks.common.ext.isValidPassword
import com.example.allticks.common.ext.passwordMatches
import com.example.allticks.common.snackbar.SnackbarManager
import com.example.allticks.model.service.AccountService
import com.example.allticks.screens.AllTicksViewModel
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(private val accountService: AccountService) :
    AllTicksViewModel() {
  var uiState = mutableStateOf(SignUpUiState())
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

  fun onRepeatPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(repeatPassword = newValue)
  }

  fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
    if (!email.isValidEmail()) {
      SnackbarManager.showMessage(R.string.email_error)
      return
    }

    if (!password.isValidPassword()) {
      SnackbarManager.showMessage(R.string.password_error)
      return
    }

    if (!password.passwordMatches(uiState.value.repeatPassword)) {
      SnackbarManager.showMessage(R.string.password_match_error)
      return
    }
    viewModelScope.launch {
      viewModelScope.launch {
        try {
          accountService.linkAccount(email, password)
          openAndPopUp(TASKS_SCREEN, SIGN_UP_SCREEN)
        } catch (e: FirebaseAuthUserCollisionException) {
          SnackbarManager.showMessage(R.string.email_already_in_use_error)
        } catch (e: Exception) {
          SnackbarManager.showMessage(R.string.unknown_error)
        }
      }
    }
  }

  fun onLogInClick(openAndPopUp: (String, String) -> Unit) {
    launchCatching { openAndPopUp(LOGIN_SCREEN, SIGN_UP_SCREEN) }
  }
}
