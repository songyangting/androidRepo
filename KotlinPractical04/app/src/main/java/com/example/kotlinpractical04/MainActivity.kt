package com.example.kotlinpractical04

import android.content.Context
import android.media.MediaActionSound
import android.media.MediaPlayer
import android.media.MediaPlayer.SEEK_CLOSEST
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.kotlinpractical04.ui.theme.KotlinPractical04Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical04Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // mediaplayer must be initialised outside of the function otherwise doesnt work
                    val mp: MediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.sippingcoffee)
                    CoffeeMediaplayer(context = this, mp)


                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoffeeMediaplayer(context: Context, mp:MediaPlayer) {


    //val mp: MediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.sippingcoffee)


    // syntax: mutableStateOf( DEFAULT VALUE )
    var isPlaying by remember { mutableStateOf(false) }

    var currentPosition by remember { mutableStateOf(0f) }

    val coroutineScope = rememberCoroutineScope()

    Column() {

        Row(
            Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {


            Image(
                painter = painterResource(id = R.drawable.coffeeimage),
                contentDescription = "sipping coffee",
                Modifier.size(width = 120.dp, height = 120.dp)
            )

            IconButton(
                onClick = {
                    isPlaying = !isPlaying //toggle
                    if (isPlaying) {
                        mp.start()
                        coroutineScope.launch {
                            while (isPlaying) {
                                currentPosition = mp.currentPosition.toFloat()
                                delay(100) // update progress every 100 ms
                                if (currentPosition == mp.duration.toFloat()) {
                                    // return slider to beginning after finishing
                                    isPlaying = !isPlaying
                                    currentPosition = 0f
                                }
                            }
                        }
                    } else {
                        mp.pause()
                    }
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            ) {

                if (!isPlaying) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "play button",
                        modifier = Modifier.size(75.dp)
                    )

                } else {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "pause button",
                        modifier = Modifier.size(75.dp)
                    )

                }

            }

            Slider(
                value = currentPosition,
                onValueChange = { newValue: Float ->
                    mp.seekTo(newValue.toInt())
                    currentPosition = newValue
                },
                valueRange = 0f..mp.duration.toFloat(),
                modifier = Modifier.width(200.dp).align(Alignment.CenterVertically)
            )

//            IconButton(onClick = { mp.pause() },
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .align(Alignment.CenterVertically)) {
//                Icon(imageVector = Icons.Filled.Star,
//                    contentDescription = "pause button",
//                    modifier = Modifier.size(75.dp))
//            }
        }

        Text("This is the sound of sipping coffee.")
    }

    DisposableEffect(Unit) {
        onDispose {
            mp.release()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Preview() {
    KotlinPractical04Theme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val mp = MediaPlayer.create(LocalContext.current, MediaActionSound.FOCUS_COMPLETE)
            CoffeeMediaplayer(LocalContext.current, mp)
        }

    }
}

