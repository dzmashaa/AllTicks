package com.example.allticks.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allticks.R
import com.example.allticks.common.composable.BasicCard
import com.example.allticks.common.composable.CancelButton
import com.example.allticks.common.composable.ConfirmButton
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.buttonLightColor
import com.example.allticks.ui.theme.primaryLight
import com.example.allticks.ui.theme.secondaryLight
import com.example.allticks.ui.theme.textColor

@Composable
fun ProfileScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState(
        initial = ProfileUiState(false)
    )
    ProfileScreenContent(
        uiState = uiState,
        onLoginClick = { viewModel.onLoginClick(openScreen)  },
        onSignUpClick = { viewModel.onSignUpClick(openScreen) },
        onSignOutClick = { viewModel.onSignOutClick(restartApp) },
        onDeleteMyAccountClick = { viewModel.onDeleteMyAccountClick(restartApp) })

}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteMyAccountClick: () -> Unit
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
            .verticalScroll(rememberScrollState())
            .padding(16.dp, 8.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Text(text = stringResource(R.string.profile),
            style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

        if (uiState.isAnonymousAccount){
            Text(text = stringResource(id = R.string.not_authorized),
                style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            BasicCard(
                title = R.string.create_account,
                content = "",
                icon = R.drawable.baseline_person_add_alt_1_24,
                onClick = { onSignUpClick () })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            BasicCard(
                title = R.string.login,
                content = "",
                icon = R.drawable.baseline_login_24,
                onClick = { onLoginClick() })
        }
        else{
            SignOutCard {
                onSignOutClick()
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            DeleteMyAccountCard {
                onDeleteMyAccountClick()
            }
        }
    }
}

@Composable
private fun SignOutCard(signOut: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    BasicCard(R.string.sign_out, R.drawable.baseline_logout_24, "", onClick = {showWarningDialog = true})

    if (showWarningDialog) {
        AlertDialog(
            title = {
                    Text(stringResource(R.string.sign_out_title),
                    color= textColor,
                    fontWeight = FontWeight.Bold) },
            text = {
                Text(stringResource(R.string.sign_out_description)) },
            dismissButton = {
                CancelButton(R.string.cancel) {
                    showWarningDialog = false } },
            confirmButton = {
                ConfirmButton(R.string.sign_out) {
                    signOut()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false },
            containerColor = primaryLight
        )
    }
}

@Composable
private fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }
    BasicCard(R.string.delete_account, R.drawable.baseline_delete_24, "", onClick = {showWarningDialog = true})
    if (showWarningDialog) {
        AlertDialog(
            title = {
                Text(
                    stringResource(R.string.delete_account_title),
                    color= textColor,
                    fontWeight = FontWeight.Bold) },
            text = {
                Text(stringResource(R.string.delete_account_description)) },
            dismissButton = {
                CancelButton(R.string.cancel) {
                    showWarningDialog = false } },
            confirmButton = {
                ConfirmButton(R.string.delete) {
                    deleteMyAccount()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false },
            containerColor = primaryLight
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val uiState = ProfileUiState(isAnonymousAccount = false)

    AllTicksTheme {
        ProfileScreenContent(
            uiState = uiState,
            onLoginClick = { },
            onSignUpClick = { },
            onSignOutClick = { },
            onDeleteMyAccountClick = { },
        )
    }
}