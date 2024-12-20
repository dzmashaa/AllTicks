package com.example.allticks.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allticks.R
import com.example.allticks.common.composable.BasicButton
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.primaryLight
import com.example.allticks.ui.theme.secondaryLight
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
  SplashScreenContent(
      onAppStart = { viewModel.onAppStart(openAndPopUp) },
      shouldShowError = viewModel.showError.value)
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
    onAppStart: () -> Unit,
    shouldShowError: Boolean
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
        if (shouldShowError) {
          Text(text = stringResource(R.string.generic_error))

          BasicButton(R.string.try_again) { onAppStart() }
        } else {
          CircularProgressIndicator(color = secondaryLight)
        }
      }

  LaunchedEffect(true) {
    delay(500)
    onAppStart()
  }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
  AllTicksTheme { SplashScreenContent(onAppStart = {}, shouldShowError = false) }
}
