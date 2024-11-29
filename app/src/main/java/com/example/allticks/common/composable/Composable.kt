package com.example.allticks.common.composable


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allticks.R
import com.example.allticks.ui.theme.buttonColor
import com.example.allticks.ui.theme.textColor

@Composable
fun EmailField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = Modifier.padding(16.dp, 4.dp),
        value = value,
        onValueChange = { onNewValue(it) },
        label = {
            Text(text = stringResource(R.string.email),
                style = MaterialTheme.typography.labelSmall)
        },
        textStyle = MaterialTheme.typography.labelSmall,
//        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email")}
    )
}

@Composable
fun PasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    PasswordField(value, R.string.password, onNewValue, modifier)
}

@Composable
fun RepeatPasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value, R.string.password_repeat, onNewValue, modifier)
}

@Composable
private fun PasswordField(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon = if(isVisible)
        painterResource(id = R.drawable.visibility)
    else
        painterResource(id = R.drawable.visibility_off_24)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = Modifier.padding(16.dp, 4.dp),
        value = value,
        onValueChange = { onNewValue(it) },
        label = { Text(text = stringResource(placeholder)) },
//        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}

@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier = Modifier, action: () -> Unit) {
    TextButton(onClick = action, modifier = modifier) {
        Text(text = stringResource(text),
            style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier = Modifier, action: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(54.dp)
        .padding(
            horizontal = 64.dp,
            vertical = 4.dp
        ),
    onClick = action,
    colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = textColor),
    ) {
        Text(text = stringResource(text),
            style = MaterialTheme.typography.bodyMedium)
    }
}