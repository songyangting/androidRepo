package com.example.practical10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    EditText edit_title, edit_content;
    Button add_btn, empty_btn;
    RecyclerView noteRecycler;
    NoteDatabase noteDatabase;
    ConstraintLayout emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.deleteDatabase("notes.db");

        edit_title = findViewById(R.id.editText);
        edit_content = findViewById(R.id.editText2);
        add_btn = findViewById(R.id.button);
        noteRecycler = findViewById(R.id.recycler_view);

        emptyLayout = findViewById(R.id.empty_layout);
        empty_btn = findViewById(R.id.empty_btn);

        empty_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Nothing to delete", Toast.LENGTH_SHORT).show();
            }
        });



        noteDatabase = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class, "notes.db").build();
        NoteDao noteDao = noteDatabase.noteDao();
        ExecutorService executorService = Executors.newSingleThreadExecutor();


        MyAdapter myAdapter = new MyAdapter(noteDao, noteRecycler, emptyLayout);
        noteRecycler.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        noteRecycler.setLayoutManager(layoutManager);

        noteDao.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                myAdapter.setNote(notes);
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add a new note to the database

                String title = edit_title.getText().toString();
                String content = edit_content.getText().toString();

                Note note = new Note(title, content);
                executorService.execute(new InsertNoteRunnable(note));

                edit_title.setText("");
                edit_content.setText("");
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