package com.example.allticks.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.allticks.R
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.allticks.ui.theme.AllTicksTheme
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.buttonColor
import com.example.allticks.ui.theme.primaryLight
import com.example.allticks.ui.theme.textColor


@Composable
fun AuthorizationScreen(
    isRegistered: Boolean,
    onButtonClicked:(String, String) -> Unit,
    onProposalButtonClicked:() -> Unit,
    modifier: Modifier = Modifier) {
    var email by rememberSaveable  { mutableStateOf("") }
    var password by rememberSaveable  { mutableStateOf("") }
    var username by rememberSaveable  { mutableStateOf("") }
    val namePage = stringResource(if (isRegistered) R.string.login else R.string.sing_up)
    val proposal = stringResource(if (isRegistered) R.string.proposal_sing_up else R.string.proposal_log_in)

    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val icon = if(passwordVisibility) 
        painterResource(id = R.drawable.visibility)
    else
        painterResource(id = R.drawable.visibility_off_24)
    Column(modifier = modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(primaryLight, backgroundLight)
            )
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(text = namePage,
            style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        if (!isRegistered){
            OutlinedTextField(
                value = username,
                onValueChange = {username = it},
                label = {
                    Text(text = stringResource(R.string.user_name),
                        style = MaterialTheme.typography.labelSmall)
                },
                textStyle = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {
                Text(text = stringResource(R.string.email),
                    style = MaterialTheme.typography.labelSmall)
            },
            textStyle = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {
                Text(text = stringResource(R.string.password),
                    style = MaterialTheme.typography.labelSmall)
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(painter = icon,
                        contentDescription = "Visibility Icon")
                    
                }
            },
            visualTransformation = if(passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(
                horizontal = 64.dp,
                vertical = 4.dp
            ),
            onClick = {
            onButtonClicked(email, password)
        },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = textColor),
            ) {
            Text(text = namePage,
                style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        
        TextButton(onClick = {
            onProposalButtonClicked()
        }) {
            Text(text = proposal,
                style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun AuthoPreview() {
    AllTicksTheme {
        AuthorizationScreen(
            isRegistered = false,
            onProposalButtonClicked = {},
            onButtonClicked = { _, _ ->},
            modifier = Modifier
            .fillMaxSize())
    }
}