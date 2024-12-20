package com.example.allticks.screens.welcome

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material3.Button
import com.example.allticks.R
import com.example.allticks.common.composable.BasicButton
import com.example.allticks.common.composable.BasicTextButton
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    openScreen: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
  val activity = LocalContext.current as? Activity ?: return
  val coroutineScope = rememberCoroutineScope()
  WelcomeScreenContent(
      onSignUpClick = { viewModel.onSignUpClick(openScreen) },
      onLogInClick = { viewModel.onLogInClick(openScreen) },
      onLogInAnonymClick = { viewModel.onAnonymAuthClick(openAndPopUp) },
      onGoogleSignInClick = {
        val googleIdOption: GetGoogleIdOption =
            GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(
                    "992974808905-sindvu0dbhqvff0m5mn4re7blvtj45q0.apps.googleusercontent.com")
                .setAutoSelectEnabled(true)
                .build()

        val request: GetCredentialRequest =
            GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

        val manager = CredentialManager.create(activity)

        coroutineScope.launch {
          try {
            val result =
                manager.getCredential(
                    request = request,
                    context = activity,
                )
            viewModel.loginWithGoogle(result.credential, openAndPopUp)
          } catch (e: GetCredentialCancellationException) {
            Log.w("GoogleSignIn", "Login canceled by user.")
          } catch (e: CancellationException) {
            Log.w("GoogleSignIn", "Login canceled: ${e.message}")
          } catch (e: Exception) {
            Log.e("GoogleSignIn", "Error when signing in with Google: ${e.message}")
          }
        }
      })
}

@Composable
fun WelcomeScreenContent(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onLogInClick: () -> Unit,
    onLogInAnonymClick: () -> Unit,
    onGoogleSignInClick: () -> Unit
) {
  Column(
      modifier =
          modifier
              .fillMaxWidth()
              .fillMaxHeight()
              .background(
                  brush = Brush.linearGradient(colors = listOf(primaryLight, backgroundLight)))
              .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.group_106__2_),
            contentDescription = null,
            modifier = Modifier.size(150.dp))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Text(
            text = stringResource(R.string.welcome_to), style = MaterialTheme.typography.titleLarge)
        Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(30.dp))

        BasicButton(R.string.login) { onLogInClick() }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        BasicButton(R.string.sing_up) { onSignUpClick() }
        BasicTextButton(text = R.string.proposal_log_in_anonym) { onLogInAnonymClick() }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        Button(
            onClick = { onGoogleSignInClick() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp, vertical = 4.dp),
        ) {
          Text(
              text = stringResource(R.string.login_with_google),
              style = MaterialTheme.typography.bodyMedium,
              textAlign = TextAlign.Center)
        }
      }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
  AllTicksTheme {
    WelcomeScreenContent(
        onSignUpClick = { },
        onLogInClick = {},
        onGoogleSignInClick = {},
        onLogInAnonymClick = {})
  }
}
