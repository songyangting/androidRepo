package com.example.kotlinpractical06

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kotlinpractical06.ui.theme.KotlinPractical06Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical06Theme {
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

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen"){
            HomeScreen(navController)
        }
        composable(
            route = "next_screen/{name}/{hobby}/{age}",
            arguments = listOf(navArgument("name") {type = NavType.StringType},
                navArgument("hobby") {type = NavType.StringType},
                navArgument("age") {type = NavType.StringType})
        ) {
            backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val hobby = backStackEntry.arguments?.getString("hobby") ?: ""
            val age = backStackEntry.arguments?.getString("age") ?: ""

            NextScreen(navController, name, hobby, age)
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController : NavController) {

    var name by remember { mutableStateOf("") }
    var hobby by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { newData -> name = newData },
            label = { Text("Enter name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = hobby,
            onValueChange = { newData -> hobby = newData },
            label = { Text("Enter hobby") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = age,
            onValueChange = { newData -> age = newData },
            label = { Text("Enter age") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = { navController.navigate("next_screen/$name/$hobby/$age") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Next Screen")
        }

    }

}


@Composable
fun NextScreen(navController: NavController, name: String, hobby: String, age: String) {

    Column() {
        Text("Name: $name")
        Text("Hobby: $hobby")
        Text("Age: $age")
        
        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Go back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPractical06Theme {
    }
}