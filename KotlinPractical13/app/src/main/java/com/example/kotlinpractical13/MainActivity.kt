package com.example.kotlinpractical13

import android.content.ContentValues.TAG
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.kotlinpractical13.ui.theme.KotlinPractical13Theme
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical13Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val database = Firebase.database
                    val notesRef = database.getReference("notes")

                    MainScreen(notesRef)
                }
            }
        }
    }
}


@Composable
fun MainScreen(notesRef : DatabaseReference) {

    var titleTxt by remember{ mutableStateOf("") }
    var contentTxt by remember{ mutableStateOf("") }

    val noteList = mutableListOf<Note>()

    notesRef.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (note in snapshot.children) {
                val noteObj: Note? = note.getValue(Note::class.java)
                noteObj?.noteId = note.key

                noteObj?.let {
                    noteList.add(it)
                    println(it)
                }
            }

        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException())
        }

    })

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            value = titleTxt,
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
                // save note to database
                val note = Note(titleTxt, contentTxt)
                val newNote = notesRef.push()
                newNote.setValue(note)

                // set key for note item
                newNote.key?.let { note.noteId = it }

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

        if (noteList.isEmpty()) {

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
                items(noteList.size) {
                        note ->
                    NoteItem(noteList[note], notesRef)
                }
            }

        }

    }

}


@Composable
fun NoteItem(note: Note, notesRef: DatabaseReference) {

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
                notesRef.child(note.noteId.toString()).removeValue()
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
fun MainScreenPreview() {
    KotlinPractical13Theme {
    }
}

