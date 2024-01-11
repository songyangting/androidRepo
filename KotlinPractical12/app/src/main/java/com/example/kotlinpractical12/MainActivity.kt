package com.example.kotlinpractical12

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.kotlinpractical12.ui.NoteViewModel
import com.example.kotlinpractical12.ui.data.Note
import com.example.kotlinpractical12.ui.theme.KotlinPractical12Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical12Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // LocalContext.current.applicationContext as Application

                    val noteViewModel = NoteViewModel(this.application)
                    MainScreen(noteViewModel)

                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: NoteViewModel) {

    val noteList = viewModel.noteList.observeAsState()

    var titleTxt by remember{ mutableStateOf("")}
    var contentTxt by remember{mutableStateOf("")}

    val scope = rememberCoroutineScope()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            value = titleTxt ,
            onValueChange = { titleTxt = it },
            label = {
                Text(text = "Enter your notes title here")
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            value = contentTxt,
            onValueChange = { contentTxt = it },
            label = {
                Text(text = "Enter your notes content here")
            }
        )


        Button(
            onClick = {
                val note = Note(title = titleTxt, content = contentTxt)
                scope.launch {
                    viewModel.insertNote(note)
                }
                titleTxt = ""
                contentTxt = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = "save button"
            )
            Text(text = "Save")
            
        }

        if (noteList.value.isNullOrEmpty()) {

            Text(
                text = "Notes Title",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            )
            Text(text = "Set up your notes content!")

            Button(
                onClick = {
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Delete, contentDescription = "delete icon")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Delete")

                }

                
            }
        } else {

            LazyColumn{
                items(noteList.value!!.size) {
                    note ->
                    NoteItem(note = noteList.value!![note], viewModel = viewModel, scope = scope)
                }
            }

        }

    }

}

@Composable
fun NoteItem(note: Note, viewModel: NoteViewModel, scope: CoroutineScope) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = note.title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )
        Text(text = note.content)

        Button(
            onClick = {
                scope.launch {
                    viewModel.deleteNote(note)
                }
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.Delete, contentDescription = "delete icon")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Delete")
            }


        }

    }

}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPractical12Theme {

    }
}