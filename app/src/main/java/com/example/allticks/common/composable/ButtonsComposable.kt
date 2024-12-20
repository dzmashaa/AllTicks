package com.example.allticks.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.allticks.R
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.buttonColor
import com.example.allticks.ui.theme.buttonLightColor
import com.example.allticks.ui.theme.textColor

@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier = Modifier, action: () -> Unit) {
  TextButton(onClick = action, modifier = modifier) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
  }
}

@Composable
fun BasicButton(@StringRes text: Int, action: () -> Unit) {
  Button(
      modifier = Modifier.fillMaxWidth().height(54.dp).padding(horizontal = 64.dp, vertical = 4.dp),
      onClick = action,
      colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = textColor),
  ) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center)
  }
}

@Composable
fun ConfirmButton(@StringRes text: Int, action: () -> Unit) {
  Button(
      modifier = Modifier.height(54.dp).padding(horizontal = 4.dp, vertical = 4.dp),
      onClick = action,
      colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = textColor),
  ) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.bodyMedium,
        color = backgroundLight,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center)
  }
}

@Composable
fun CancelButton(@StringRes text: Int, action: () -> Unit) {
  Button(
      modifier =
          Modifier.height(54.dp)
              .padding(horizontal = 4.dp, vertical = 4.dp)
              .border(width = 2.dp, color = buttonColor, shape = MaterialTheme.shapes.extraLarge),
      onClick = action,
      colors =
          ButtonDefaults.buttonColors(
              containerColor = backgroundLight, contentColor = buttonLightColor),
  ) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        color = buttonColor,
        fontWeight = FontWeight.Bold)
  }
}
