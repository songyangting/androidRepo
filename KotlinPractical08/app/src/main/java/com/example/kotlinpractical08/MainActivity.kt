package com.example.kotlinpractical08

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kotlinpractical08.ui.theme.KotlinPractical08Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical08Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen/{displayText}") {
        composable(
            route = "main_screen/{displayText}",
            arguments = listOf(navArgument("displayText") { type = NavType.StringType})
        ) {
            backStackEntry ->
            val displayText = backStackEntry.arguments?.getString("displayText") ?: "Enter some text by clicking on the dialog."
            MainScreen(navController = navController, displayText)
        }
        composable("input_dialog") {
            InputDialog(navController)
        }


    }
}

@Composable
fun MainScreen(navController: NavController, displayText: String) {

    //var showDialog by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = displayText,
            modifier = Modifier
                .padding(5.dp))
        Button(
            onClick = { navController.navigate("input_dialog")},

        ) {
            Text("Show Dialog")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(navController: NavController, onDismissRequest: () -> Unit = {navController.navigateUp()}) {

    var inputText by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { onDismissRequest() },
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Input Dialog",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )


            TextField(
                value = inputText ,
                onValueChange = {newData: String -> inputText = newData},
                label = {
                    Text("Enter text")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Row (
                modifier = Modifier

                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    onDismissRequest()
                }) {
                    Text("Dismiss")
                }

                Button(onClick = { navController.navigate("main_screen/$inputText") }) {
                    Text("Submit")
                }
            }


        }



    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPractical08Theme {
    }
}