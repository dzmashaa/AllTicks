package com.example.allticks.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allticks.R
import com.example.allticks.common.composable.BasicButton
import com.example.allticks.common.composable.BasicTextButton
import com.example.allticks.common.composable.EmailField
import com.example.allticks.common.composable.PasswordField
import com.example.allticks.common.composable.RepeatPasswordField
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) },
        onLogInClick = { viewModel.onLogInClick(openAndPopUp) },
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onLogInClick:() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(primaryLight, backgroundLight)
                )
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = stringResource(R.string.sing_up),
            style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        EmailField(uiState.email, onEmailChange)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

        PasswordField(uiState.password, onPasswordChange)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

        RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        BasicButton(R.string.create_account) { onSignUpClick() }
        BasicTextButton(text = R.string.proposal_log_in) {
            onLogInClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )
    AllTicksTheme {
        SignUpScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onRepeatPasswordChange = { },
            onSignUpClick = { },
            onLogInClick = {}
        )
    }
}
