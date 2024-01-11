package com.example.practical11;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edit_title, edit_content;
    Button add_btn, delete_btn;
    RecyclerView noteRecycler;
    ConstraintLayout emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_title = findViewById(R.id.editText);
        edit_content = findViewById(R.id.editText2);add_btn = findViewById(R.id.button);
        emptyView = findViewById(R.id.empty_layout);
        delete_btn= findViewById(R.id.empty_btn);


        // initialise and access the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notesRef = database.getReference("notes");

        noteRecycler = findViewById(R.id.recycler_view);
        MyAdapter myAdapter = new MyAdapter(notesRef, noteRecycler, emptyView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        noteRecycler.setLayoutManager(layoutManager);
        noteRecycler.setAdapter(myAdapter);

        // Write a message to the database
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edit_title.getText().toString();
                String content = edit_content.getText().toString();

                Note note = new Note(title, content);

                DatabaseReference newNote = notesRef.push();
                String noteID = newNote.getKey();
                // is there a way to retrieve the unique ID without having to set explicitly?

                newNote.setValue(note);
                Log.i("New note ID", noteID);

                edit_title.setText("");
                edit_content.setText("");


            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Nothing to delete", Toast.LENGTH_SHORT).show();
            }
        });

        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Note> noteList = new ArrayList<Note>();

                for (DataSnapshot note : snapshot.getChildren()){
                    Note noteObj = note.getValue(Note.class);
                   // note.getKey();
                    noteObj.setKey(note.getKey());

                    if (noteObj != null) {
                        noteList.add(noteObj);
                    }
                }
                myAdapter.setNote(noteList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // failed to read value

                // what does this line mean?
                Log.w(TAG, "Failed to read value", error.toException());
            }
        });




    }
}