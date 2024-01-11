package com.example.kotlinpractical03

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.kotlinpractical03.ui.theme.KotlinPractical03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical03Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Paupau")
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text("Hello, $name!")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPractical03Theme {
        Greeting("Android")
    }
}

@Composable
fun MainScreen() {
    ComposableLifecycle {source, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.i("Lifecycle", "Activity created")
            }

            Lifecycle.Event.ON_START -> {
                Log.i("Lifecycle", "Activity started")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.i("Lifecycle", "Activity resumed")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.i("Lifecycle", "Activity paused")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.i("Lifecycle", "Activity stopped")
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.i("Lifecycle", "Activity destroyed")
            }
            else -> {
                Log.i("Lifecycle", "Unknown lifecycle event...")
            }
        }
    }
}



@Composable
// LifecycleOwner has a default value of the current lifecycle owner
// onEvent is a callback function that is triggered when a lifecycle event occurs
fun ComposableLifecycle(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
                        onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit) {

    DisposableEffect(lifecycleOwner) {

        // a LifecycleEventObserver that triggers the onEvent callback when a lifecycle event occurs is created
        val observer = LifecycleEventObserver { lifecycleOwner, event -> onEvent(lifecycleOwner, event)}

        lifecycleOwner.lifecycle.addObserver(observer)

        // remove observer from the lifecycle when the composable leaves the composition.
        // important to prevent memory leaks
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

}