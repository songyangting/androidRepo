package com.example.kotlinpractical10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlinpractical10.ui.theme.KotlinPractical10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical10Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(TaskViewModel())

                }
            }
        }
    }
}



@Composable
fun MainScreen(viewModel: TaskViewModel) {

    var taskList = viewModel.getTaskList()

    var showDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Add Task")
        }
        
        Text(
            text = "Task List",
            modifier = Modifier
                .padding(bottom = 10.dp, start = 16.dp, end = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        if (taskList.isEmpty()) {
            // how to center in the middle of the whole page?
            Text(
                text = "No tasks currently.",
                modifier = Modifier
                    .fillMaxSize(),
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

        } else {
            LazyColumn {
                items(taskList) {
                        task -> TaskListItem(task)
                }
            }
        }
        
    }
    
    if (showDialog) {
        AddTaskDialog(viewModel) {
            // dismiss request
            showDialog = false
        }
    }


    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(viewModel: TaskViewModel, onRequestDismissed: () -> Unit) {

    var taskTxt by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onRequestDismissed() },
        dismissButton = {
            IconButton(onClick = { onRequestDismissed() }) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = "confirm button")
            }
        },
        confirmButton = {
            IconButton(onClick = {
                viewModel.setTask(taskTxt)
                onRequestDismissed()
            }) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "confirm button")
            }
        },

        title = {
            Text(text = "Add Task")
        },

        text = {
            TextField(
                value = taskTxt,
                onValueChange = {taskTxt = it},
                label = {
                    Text(text = "Enter task")
                }
            )
        }
    )



}

@Composable
fun TaskListItem(task: String) {
    Card (
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp)
            .wrapContentHeight()
    ) {
        Text(
            text = task,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}
