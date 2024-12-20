package com.example.allticks.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allticks.R
import com.example.allticks.common.composable.BasicButton
import com.example.allticks.common.composable.BasicTextButton
import com.example.allticks.common.composable.EmailField
import com.example.allticks.common.composable.PasswordField
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState
  LoginScreenContent(
      uiState = uiState,
      onEmailChange = viewModel::onEmailChange,
      onPasswordChange = viewModel::onPasswordChange,
      onLogInClick = { viewModel.onLogInClick(openAndPopUp) },
      onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) },
      onForgotPasswordClick = viewModel::onForgotPasswordClick)
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
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
        Text(text = stringResource(R.string.login), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        EmailField(uiState.email, onEmailChange)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        PasswordField(uiState.password, onPasswordChange)
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 0.dp, end = 40.dp),
            horizontalArrangement = Arrangement.End) {
              TextButton(
                  onClick = { onForgotPasswordClick() },
                  modifier = Modifier.padding(vertical = 0.dp),
              ) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(vertical = 0.dp),
                )
              }
            }
        BasicButton(R.string.login) { onLogInClick() }
        BasicTextButton(text = R.string.proposal_sing_up) { onSignUpClick() }
      }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
  val uiState = LoginUiState(email = "email@test.com")
  AllTicksTheme {
    LoginScreenContent(
        uiState = uiState,
        onEmailChange = {},
        onPasswordChange = {},
        onLogInClick = {},
        onSignUpClick = {},
        onForgotPasswordClick = {})
  }
}
