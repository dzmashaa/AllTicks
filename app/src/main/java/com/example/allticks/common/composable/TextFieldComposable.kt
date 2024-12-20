package com.example.allticks.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.allticks.R
import com.example.allticks.ui.theme.backgroundLight
import com.example.allticks.ui.theme.textColor
import com.example.allticks.ui.theme.textLightColor
import com.example.allticks.ui.theme.whiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicField(
    @StringRes text: Int,
    value: String,
    onNewValue: (String) -> Unit,
) {
  OutlinedTextField(
      singleLine = true,
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 3.dp).fillMaxWidth().height(60.dp),
      value = value,
      shape = MaterialTheme.shapes.extraLarge,
      onValueChange = { onNewValue(it) },
      colors =
          TextFieldDefaults.textFieldColors(
              containerColor = whiteColor, focusedTextColor = textLightColor),
      textStyle = MaterialTheme.typography.bodyMedium,
  )
}

@Composable
fun EmailField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
  OutlinedTextField(
      singleLine = true,
      modifier = Modifier.padding(16.dp, 4.dp),
      value = value,
      onValueChange = { onNewValue(it) },
      label = {
        Text(text = stringResource(R.string.email), style = MaterialTheme.typography.labelSmall)
      },
      textStyle = MaterialTheme.typography.labelSmall,
      //        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription =
      // "Email")}
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

  val icon =
      if (isVisible) painterResource(id = R.drawable.visibility)
      else painterResource(id = R.drawable.visibility_off_24)

  val visualTransformation =
      if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

  OutlinedTextField(
      modifier = Modifier.padding(16.dp, 4.dp),
      value = value,
      onValueChange = { onNewValue(it) },
      label = {
        Text(text = stringResource(placeholder), style = MaterialTheme.typography.labelSmall)
      },
      //        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock")
      // },
      trailingIcon = {
        IconButton(onClick = { isVisible = !isVisible }) {
          Icon(painter = icon, contentDescription = "Visibility", tint = textColor)
        }
      },
      textStyle = MaterialTheme.typography.labelSmall,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      visualTransformation = visualTransformation)
}

@Composable
fun BasicCard(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
  Card(
      elevation = CardDefaults.cardElevation(10.dp),
      modifier =
          Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 3.dp).clickable {
            onClick()
          },
      shape = RoundedCornerShape(20.dp),
      colors =
          CardDefaults.cardColors(
              containerColor = backgroundLight,
          ),
  ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(16.dp)) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
                stringResource(title),
                color = textColor,
                style = MaterialTheme.typography.bodyLarge)
          }

          if (content.isNotBlank()) {
            Text(
                text = content,
                modifier = Modifier.padding(16.dp, 0.dp),
                style = MaterialTheme.typography.labelSmall,
                color = textLightColor)
          }
          Icon(
              painter = painterResource(icon),
              contentDescription = "Icon",
              tint = textColor,
              modifier = Modifier.size(27.dp))
        }
  }
}
