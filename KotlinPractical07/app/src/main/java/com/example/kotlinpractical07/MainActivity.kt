package com.example.kotlinpractical07

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kotlinpractical07.ui.theme.KotlinPractical07Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_CODE
        )
        setContent {
            KotlinPractical07Theme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(LocalContext.current)

                }
            }
        }
    }
    
    
}

private const val REQUEST_CODE = 1
private const val NOTIFICATION_ID = 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(context: Context) {




    var notifTitle by remember { mutableStateOf("") }
    var notifContent by remember { mutableStateOf("") }

    val notification = NotificationBuilder(
        context = context,
        channelId = CHANNEL_ID,
        smallIconResId = androidx.core.R.drawable.notify_panel_notification_icon_bg,
        textTitle = notifTitle,
        textContent = notifContent
    )

    Column() {
        OutlinedTextField(
            value = notifTitle ,
            onValueChange = {newData -> notifTitle = newData },
            label = { Text("Notification Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = notifContent,
            onValueChange = {newData -> notifContent = newData},
            label = { Text("Notification Content") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                      with(NotificationManagerCompat.from(context)) {
                          if (ActivityCompat.checkSelfPermission(
                                  context,
                                  Manifest.permission.POST_NOTIFICATIONS
                              ) != PackageManager.PERMISSION_GRANTED
                          ) {
                              println("Permission to show notifications not granted.")

                          }
                          notify(NOTIFICATION_ID, notification.build())
                      }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text ("Build Notification")

        }


    }
}


private const val CHANNEL_ID = "my_channel_id"

@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(context: Context) {
    var name = "channel_name"
    val descriptionText = "channel_desc"
    val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
        description = descriptionText
    }

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

// why must this be a composable function?
@Composable
fun NotificationBuilder(
    context: Context,
    channelId: String,
    @DrawableRes smallIconResId: Int,
    textTitle: String,
    textContent: String
): NotificationCompat.Builder {
    return NotificationCompat.Builder(context, channelId)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setSmallIcon(smallIconResId)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPractical07Theme {
    }
}