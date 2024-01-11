package com.example.kotlinpractical11

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlinpractical11.ui.theme.KotlinPractical11Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical11Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // avoid creating more than 1 instance of datastore
    val datastore = StoreBackgroundColor(context)

    //get saved colour & status
    val savedBackgroundColour = datastore.getBackgroundColour.collectAsState(initial = "").value
    val savedSwitchStatus = datastore.isSwitchOn.collectAsState(initial = false).value

    var colorCodeInput by remember { mutableStateOf("") }
    var showColorSaved by remember { mutableStateOf(false) }
    var isOn by remember { mutableStateOf(false) }

    var bgColour = if (savedSwitchStatus) Color(
        android.graphics.Color.parseColor(
            savedBackgroundColour!!
        )
    ) else androidx.compose.ui.graphics.Color.White

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = bgColour
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 12.dp, top = 12.dp),
                value = colorCodeInput,
                onValueChange = { colorCodeInput = it },
                label = {
                    Text(text = "New color code")
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                onClick = {
                    try {
                        android.graphics.Color.parseColor(colorCodeInput)

                        showColorSaved = true
                        scope.launch {
                            datastore.saveBackgroundColour(colorCodeInput)
                        }

                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(context, "Invalid hex code", Toast.LENGTH_SHORT).show()
                        colorCodeInput = ""
                    }

                }
            ) {
                Text(text = "Save")
            }

            AnimatedVisibility(visible = showColorSaved) {
                Text(
                    text = "Colour saved: $colorCodeInput",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    textAlign = TextAlign.Center
                )
            }


            Text(
                text = "Toggle background colour",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Switch(
                checked = savedSwitchStatus,
                onCheckedChange = {
                    isOn = it
                    showColorSaved = false
                    scope.launch {
                        datastore.setSwitchOn(isOn)
                    }
                }
            )

        }

    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPractical11Theme {
        MainScreen()
    }
}