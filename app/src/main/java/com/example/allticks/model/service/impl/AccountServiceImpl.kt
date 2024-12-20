package com.example.allticks.model.service.impl

import com.example.allticks.model.User
import com.example.allticks.model.service.AccountService
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AccountServiceImpl
@Inject
constructor(
    private val auth: FirebaseAuth,
) : AccountService {
  override val currentUserId: String
    get() = auth.currentUser?.uid.orEmpty()

  override val hasUser: Boolean
    get() = auth.currentUser != null

  override val currentUser: Flow<User>
    get() = callbackFlow {
      val listener =
          FirebaseAuth.AuthStateListener { auth ->
            this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
          }
      auth.addAuthStateListener(listener)
      awaitClose { auth.removeAuthStateListener(listener) }
    }

  override suspend fun signInWithGoogle(idToken: String) {
    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
    //        auth.signInWithCredential(firebaseCredential)

    val currentUser = auth.currentUser

    if (currentUser?.isAnonymous == true) {
      try {
        currentUser.linkWithCredential(firebaseCredential).await()
      } catch (e: FirebaseAuthUserCollisionException) {
        auth.signInWithCredential(firebaseCredential).await()
      }
    } else {
      auth.signInWithCredential(firebaseCredential).await()
    }
  }

  override suspend fun authenticate(email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password).await()
  }

  override suspend fun createAnonymousAccount() {
    auth.signInAnonymously().await()
  }

  override suspend fun linkAccount(email: String, password: String) {
    if (auth.currentUser == null) {
      createAnonymousAccount()
    }

    val credential = EmailAuthProvider.getCredential(email, password)
    auth.currentUser!!.linkWithCredential(credential).await()
  }

  override suspend fun deleteAccount() {
    auth.currentUser!!.delete().await()
  }

  override suspend fun signOut() {
    if (auth.currentUser!!.isAnonymous) {
      auth.currentUser!!.delete()
    }
    auth.signOut()
  }
  override suspend fun sendRecoveryEmail(email: String) {
    auth.sendPasswordResetEmail(email).await()
  }
}
