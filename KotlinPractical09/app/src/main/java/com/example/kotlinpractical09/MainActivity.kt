package com.example.kotlinpractical09

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlinpractical09.ui.theme.KotlinPractical09Theme
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical09Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TimerApp(LocalContext.current)

                }
            }
        }
    }
}

@Composable
fun TimerApp(context: Context) {
    var isTimerRunning by remember { mutableStateOf(false) }

    val countDownTimer = object: CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            if (isTimerRunning) {
                // timer has not been stopped
                Toast.makeText(context, "Task will run for 3 seconds...", Toast.LENGTH_SHORT).show()
                this.start()
            } else {
                this.cancel()
            }
        }

    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isTimerRunning) "Timer is running" else "Timer is stopped",
            modifier = Modifier
                .padding(10.dp)
        )

        Button(onClick = {
            isTimerRunning = !isTimerRunning
            if (isTimerRunning) {
                Toast.makeText(context, "Task will run for 3 seconds...", Toast.LENGTH_SHORT).show()
                countDownTimer.start()
            } else {
                countDownTimer.cancel()
            }

        }) {

            Text(text = if (isTimerRunning) "Stop Timer" else "Start timer")

        }


    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPractical09Theme {

    }
}