package com.example.kotlinpractical13

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.kotlinpractical13.ui.theme.KotlinPractical13Theme
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
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

                    MainScreen(LocalContext.current, notesRef)
                }
            }
        }
    }
}


@Composable
fun MainScreen(context: Context, notesRef : DatabaseReference) {

    var titleTxt by remember{ mutableStateOf("") }
    var contentTxt by remember{ mutableStateOf("") }

    var noteList = remember {mutableStateListOf<Note>()}

    notesRef.addChildEventListener(object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            // dataSnapshot contains data for the new child
            val newNote = snapshot.getValue(Note::class.java)
            if (newNote != null) {
                newNote.noteId = snapshot.key

                //Log.d("new note", newNote.toString())

                // why is there an infinite loop of adding new note without this line?????
                val existingNote = noteList.firstOrNull {it.noteId == snapshot.key}

                if (existingNote == null) {
                    noteList.add(0, newNote)
                }
            }
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            noteList.removeIf { it.noteId == snapshot.key }
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
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
                val note = Note(title = titleTxt, content = contentTxt)
                val newNote = notesRef.push()
                newNote.setValue(note)

                // set key for note item
                note.noteId = newNote.key.toString()
               // Log.d("note item", note.toString())

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
                    Toast.makeText(context,"Nothing to delete", Toast.LENGTH_SHORT ).show()
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

