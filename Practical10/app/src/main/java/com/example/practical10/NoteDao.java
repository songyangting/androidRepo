package com.example.practical10;

import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();


    // use : notation to represent parsed data
    @Query("SELECT * FROM notes WHERE notes.id = :id")
    Note getNoteById(int id);

}
