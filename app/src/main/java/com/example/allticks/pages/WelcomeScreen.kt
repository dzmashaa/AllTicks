package com.example.allticks.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.allticks.R
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.buttonColor
import com.example.allticks.ui.theme.primaryLight
import com.example.allticks.ui.theme.textColor

@Composable
fun WelcomeScreen(onSingUpButtonClicked:() -> Unit,
                  onLogInButtonClicked:() -> Unit,
                  modifier: Modifier = Modifier) {
    val buttonModifier = Modifier
        .fillMaxWidth()
        .height(54.dp)
        .padding(
            horizontal = 40.dp,
            vertical = 4.dp
        )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(primaryLight, backgroundLight)
            )
        ),
        contentAlignment = Alignment.Center)
    {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))){
            Image(painter = painterResource(R.drawable.group_106__2_),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
                )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.welcome_to),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(onClick = { onSingUpButtonClicked()},
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = textColor)
                ) {
                Text(text = stringResource(R.string.login),
                    style = MaterialTheme.typography.bodyMedium
                    )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Button(onClick = { onLogInButtonClicked()},
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = textColor)) {
                Text(text = stringResource(R.string.sing_up),
                    style = MaterialTheme.typography.bodyMedium)
            }
        }

    }


}

@Preview
@Composable
fun WelcomePreview(showBackground: Boolean = true) {
    AllTicksTheme {
        WelcomeScreen(
            onLogInButtonClicked = {},
            onSingUpButtonClicked = {})
    }
}