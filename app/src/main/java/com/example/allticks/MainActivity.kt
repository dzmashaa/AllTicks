package com.example.allticks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.allticks.ui.theme.AllTicksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            AllTicksTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                        AllTicksApp(modifier = Modifier.padding(innerPadding))
//                }
//            }
            AllTicksApp()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AllTicksTheme {

    }
}