package com.example.practical10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    EditText edit_title, edit_content;
    Button add_btn;
    RecyclerView noteRecycler;
    NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_title = findViewById(R.id.editText);
        edit_content = findViewById(R.id.editText2);
        add_btn = findViewById(R.id.button);
        noteRecycler = findViewById(R.id.recycler_view);


        noteDatabase = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class, "notes.db").build();
        NoteDao noteDao = noteDatabase.noteDao();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        List<Note> noteList = noteDatabase.noteDao().getAllNotes();


        MyAdapter myAdapter = new MyAdapter(noteList, getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        noteRecycler.setLayoutManager(layoutManager);


        noteRecycler.setAdapter(myAdapter);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add a new note to the database

                String title = edit_title.getText().toString();
                String content = edit_content.getText().toString();
                int id = (noteList.size() + 1);

                Note note = new Note(id, title, content);
                executorService.execute(new InsertNoteRunnable(note));
            }
        });

    }

    private class InsertNoteRunnable implements Runnable {
        private final Note note;

        InsertNoteRunnable(Note note) {
            this.note = note;
        }

        @Override
        public void run() {
            noteDatabase.noteDao().insert(note);
        }
    }



}