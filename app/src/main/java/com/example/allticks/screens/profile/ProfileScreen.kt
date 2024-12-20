package com.example.allticks.screens.profile

import android.app.Activity
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allticks.R
import com.example.allticks.common.composable.BasicButton
import com.example.allticks.common.composable.BasicCard
import com.example.allticks.common.composable.CancelButton
import com.example.allticks.common.composable.ConfirmButton
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight
import com.example.allticks.ui.theme.textColor
import com.example.allticks.ui.theme.textLightColor
import com.example.allticks.ui.theme.whiteColor
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState(initial = ProfileUiState(false))
  val categories by viewModel.categories.collectAsState()
  val activity = LocalContext.current as? Activity ?: return
  val coroutineScope = rememberCoroutineScope()
  ProfileScreenContent(
      uiState = uiState,
      categories = categories,
      onLoginClick = { viewModel.onLoginClick(openScreen) },
      onSignUpClick = { viewModel.onSignUpClick(openScreen) },
      onSignOutClick = { viewModel.onSignOutClick(restartApp) },
      onDeleteMyAccountClick = { viewModel.onDeleteMyAccountClick(restartApp) },
      onRemoveCategoryClick = viewModel::removeCategory,
      onAddCategoryClick = viewModel::addCategory,
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
            viewModel.loginWithGoogle(result.credential, openScreen)
          } catch (e: GetCredentialCancellationException) {
            Log.w("GoogleSignIn", "Login canceled by user.")
          } catch (e: Exception) {
            Log.e("GoogleSignIn", "Error when signing in with Google: ${e.message}")
          }
        }
      })
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    categories: List<String>,
    uiState: ProfileUiState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteMyAccountClick: () -> Unit,
    onRemoveCategoryClick: (String) -> Unit,
    onAddCategoryClick: (String) -> Unit,
    onGoogleSignInClick: () -> Unit
) {

  Column(
      modifier =
          modifier
              .fillMaxWidth()
              .fillMaxHeight()
              .background(
                  brush = Brush.linearGradient(colors = listOf(primaryLight, backgroundLight)))
              .verticalScroll(rememberScrollState())
              .padding(16.dp, 8.dp),
      horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Text(text = stringResource(R.string.profile), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

        if (uiState.isAnonymousAccount) {
          Text(
              text = stringResource(id = R.string.not_authorized),
              style = MaterialTheme.typography.labelSmall)
          Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
          BasicCard(
              title = R.string.create_account,
              content = "",
              icon = R.drawable.baseline_person_add_alt_1_24,
              onClick = { onSignUpClick() })
          Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
          BasicCard(
              title = R.string.login_with_email,
              content = "",
              icon = R.drawable.baseline_login_24,
              onClick = { onLoginClick() })
          Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
          BasicCard(
              title = R.string.login_google,
              content = "",
              icon = R.drawable.google,
              onClick = { onGoogleSignInClick() })
        } else {
          SignOutCard { onSignOutClick() }
          Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
          DeleteMyAccountCard { onDeleteMyAccountClick() }
          Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
          ManageCategories(categories, onRemoveCategoryClick, onAddCategoryClick)
        }
      }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategories(
    categories: List<String>,
    onRemoveCategoryClick: (String) -> Unit,
    onAddCategoryClick: (String) -> Unit,
) {
  var showDialog by remember { mutableStateOf(false) }
  var newCategory by remember { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
        stringResource(id = R.string.manage_categories),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
    categories.forEach { category ->
      if (category != "None") {
        Card(
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.padding(horizontal = 60.dp, vertical = 3.dp),
            shape = RoundedCornerShape(20.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = backgroundLight,
                )) {
              Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp)) {
                    Text(
                        category,
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f))
                    IconButton(onClick = { onRemoveCategoryClick(category) }) {
                      Icon(
                          Icons.Default.Delete,
                          contentDescription = stringResource(id = R.string.delete),
                          tint = textColor)
                    }
                  }
            }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
      }
    }
    BasicButton(text = R.string.add_category) { showDialog = true }
    if (showDialog) {
      AlertDialog(
          onDismissRequest = { showDialog = false },
          title = { Text(text = stringResource(id = R.string.enter_category_name)) },
          text = {
            TextField(
                value = newCategory,
                onValueChange = { newCategory = it },
                placeholder = {
                  Text(
                      stringResource(id = R.string.new_category),
                      style = MaterialTheme.typography.bodyMedium)
                },
                colors =
                    TextFieldDefaults.textFieldColors(
                        containerColor = whiteColor, focusedTextColor = textLightColor),
            )
          },
          confirmButton = {
            TextButton(
                onClick = {
                  onAddCategoryClick(newCategory)
                  newCategory = ""
                  showDialog = false
                }) {
                  Text(stringResource(id = R.string.add))
                }
          },
          dismissButton = {
            TextButton(onClick = { showDialog = false }) {
              Text(stringResource(id = R.string.cancel))
            }
          },
          containerColor = backgroundLight)
    }
  }
}

@Composable
private fun SignOutCard(signOut: () -> Unit) {
  var showWarningDialog by remember { mutableStateOf(false) }

  BasicCard(
      R.string.sign_out, R.drawable.baseline_logout_24, "", onClick = { showWarningDialog = true })

  if (showWarningDialog) {
    AlertDialog(
        title = {
          Text(
              stringResource(R.string.sign_out_title),
              color = textColor,
              fontWeight = FontWeight.Bold)
        },
        text = { Text(stringResource(R.string.sign_out_description)) },
        dismissButton = { CancelButton(R.string.cancel) { showWarningDialog = false } },
        confirmButton = {
          ConfirmButton(R.string.sign_out) {
            signOut()
            showWarningDialog = false
          }
        },
        onDismissRequest = { showWarningDialog = false },
        containerColor = primaryLight)
  }
}

@Composable
private fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
  var showWarningDialog by remember { mutableStateOf(false) }
  BasicCard(
      R.string.delete_account,
      R.drawable.baseline_delete_24,
      "",
      onClick = { showWarningDialog = true })
  if (showWarningDialog) {
    AlertDialog(
        title = {
          Text(
              stringResource(R.string.delete_account_title),
              color = textColor,
              fontWeight = FontWeight.Bold)
        },
        text = { Text(stringResource(R.string.delete_account_description)) },
        dismissButton = { CancelButton(R.string.cancel) { showWarningDialog = false } },
        confirmButton = {
          ConfirmButton(R.string.delete) {
            deleteMyAccount()
            showWarningDialog = false
          }
        },
        onDismissRequest = { showWarningDialog = false },
        containerColor = primaryLight)
  }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
  val uiState = ProfileUiState(isAnonymousAccount = true)
  val categories = listOf("Work", "Personal")
  AllTicksTheme {
    ProfileScreenContent(
        uiState = uiState,
        onLoginClick = {},
        onSignUpClick = {},
        onSignOutClick = {},
        onDeleteMyAccountClick = {},
        categories = categories,
        onRemoveCategoryClick = {},
        onAddCategoryClick = {},
        onGoogleSignInClick = {})
  }
}
