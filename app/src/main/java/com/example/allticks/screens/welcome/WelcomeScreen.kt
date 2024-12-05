package com.example.allticks.screens.welcome


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allticks.R
import com.example.allticks.common.composable.BasicButton
import com.example.allticks.common.composable.BasicTextButton
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight


@Composable
fun WelcomeScreen(
    openScreen: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    WelcomeScreenContent(
        onSignUpClick = { viewModel.onSignUpClick(openScreen) },
        onLogInClick = { viewModel.onLogInClick(openScreen) },
        onLogInAnonymClick = {viewModel.onAnonymAuthClick(openAndPopUp)}
    )
}

@Composable
fun WelcomeScreenContent(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onLogInClick:() -> Unit,
    onLogInAnonymClick:() -> Unit
){
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
    ) {

        Image(
            painter = painterResource(R.drawable.group_106__2_),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Text(
            text = stringResource(R.string.welcome_to),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(30.dp))

        BasicButton(R.string.login) { onLogInClick() }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        BasicButton(R.string.sing_up) { onSignUpClick() }
        BasicTextButton(text = R.string.proposal_log_in_anonym) {
            onLogInAnonymClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    AllTicksTheme {
        WelcomeScreenContent(
            onSignUpClick = { /*TODO*/ },
            onLogInClick = { /*TODO*/ }) {
            
        }
    }
}
