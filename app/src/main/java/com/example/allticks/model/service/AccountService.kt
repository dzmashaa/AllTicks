package com.example.allticks.model.service

import com.example.allticks.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
  val currentUserId: String
  val hasUser: Boolean

  val currentUser: Flow<User>

  suspend fun authenticate(email: String, password: String)
  suspend fun createAnonymousAccount()
  suspend fun linkAccount(email: String, password: String)
  suspend fun deleteAccount()
  suspend fun signOut()
  suspend fun sendRecoveryEmail(email: String)
  suspend fun signInWithGoogle(idToken: String)
}
