package com.example.kotlinpractical15

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlinpractical15.ui.theme.KotlinPractical15Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical15Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    WelcomeTextComposable()
                }
            }
        }
    }
}

@Composable
fun WelcomeTextComposable() {
    Text("Welcome to Compose")
}

@Composable
fun ClickableTextComposable() {
    var text by remember { mutableStateOf("Not Clicked") }
    Column {
        Button(onClick = { text = "Clicked" }) {
            Text("Click Me")
        }
        Text(text) }
}

@Composable
fun InputFieldComposable() {
    var text by remember { mutableStateOf("") }
    Column {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter text") }
        )
        Text("You entered: $text")
    }
}

@Composable
fun LazyColumnComposable() {
    LazyColumn {
        items(100) { index ->
            Text("Item $index")
        } }
}

@Composable
fun ToggleSwitchComposable() {
    var isChecked by remember { mutableStateOf(false) }
    Column {
        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )
        Text(if (isChecked) "ON" else "OFF")
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPractical15Theme {

    }
}