package com.example.allticks.screens.welcome

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.example.allticks.LOGIN_SCREEN
import com.example.allticks.SIGN_UP_SCREEN
import com.example.allticks.TASKS_SCREEN
import com.example.allticks.WELCOME_SCREEN
import com.example.allticks.model.service.AccountService
import com.example.allticks.screens.AllTicksViewModel
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel
@Inject
constructor(
    private val accountService: AccountService,
) : AllTicksViewModel() {

  fun loginWithGoogle(credential: Credential, openAndPopUp: (String, String) -> Unit) {
    if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
      val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
      launchCatching {
        accountService.signInWithGoogle(googleIdToken.idToken)
        openAndPopUp(TASKS_SCREEN, WELCOME_SCREEN)
      }
    }
  }

  fun onSignUpClick(navigate: (String) -> Unit) {
    launchCatching { navigate(SIGN_UP_SCREEN) }
  }

  fun onLogInClick(navigate: (String) -> Unit) {
    launchCatching { navigate(LOGIN_SCREEN) }
  }
  fun onAnonymAuthClick(openAndPopUp: (String, String) -> Unit) {
    launchCatching {
      accountService.createAnonymousAccount()
      openAndPopUp(TASKS_SCREEN, WELCOME_SCREEN)
    }
  }
}
