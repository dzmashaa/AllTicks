package com.example.allticks.screens.profile

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.viewModelScope
import com.example.allticks.LOGIN_SCREEN
import com.example.allticks.R
import com.example.allticks.SIGN_UP_SCREEN
import com.example.allticks.SPLASH_SCREEN
import com.example.allticks.TASKS_SCREEN
import com.example.allticks.common.snackbar.SnackbarManager
import com.example.allticks.model.service.AccountService
import com.example.allticks.model.service.StorageService
import com.example.allticks.screens.AllTicksViewModel
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val storageService: StorageService,
    private val accountService: AccountService
) : AllTicksViewModel() {
  val uiState = accountService.currentUser.map { ProfileUiState(it.isAnonymous) }
  private val _categories = MutableStateFlow<List<String>>(listOf("None", "Personal", "Work"))
  val categories: StateFlow<List<String>> = _categories
  init {
    loadCategories()
  }

  private fun loadCategories() {
    viewModelScope.launch {
      val userId = accountService.currentUserId
      if (userId.isNotEmpty()) {
        val userCategories = storageService.getUserCategories(userId)
        _categories.value = userCategories ?: listOf("None", "Personal", "Work")
      }
    }
  }
  fun loginWithGoogle(credential: Credential, openAndPopUp: (String) -> Unit) {
    if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
      val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
      launchCatching {
        accountService.signInWithGoogle(googleIdToken.idToken)
        openAndPopUp(TASKS_SCREEN)
      }
    }
  }
  fun addCategory(category: String) {
    if (category.length > 10) {
      SnackbarManager.showMessage(R.string.category_too_long)
      return
    }
    if (category.isNotBlank() && !_categories.value.contains(category)) {
      val updatedCategories = _categories.value + category
      _categories.value = updatedCategories
      saveCategoriesToFirebase(updatedCategories)
    }
  }

  fun removeCategory(category: String) {
    val updatedCategories = _categories.value - category
    _categories.value = updatedCategories
    saveCategoriesToFirebase(updatedCategories)
  }

  private fun saveCategoriesToFirebase(categories: List<String>) {
    viewModelScope.launch {
      val userId = accountService.currentUserId
      if (userId.isNotEmpty()) {
        storageService.updateUserCategories(userId, categories)
      }
    }
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
