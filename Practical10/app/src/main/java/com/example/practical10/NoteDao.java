package com.example.practical10;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDao {
    //@Insert (onConflict = OnConflictStrategy.REPLACE)
    //long insert(Note note); // return noteID

    @Insert
    void insert(Note note);

    /*
    After updating DAO, build project
    Room uses annotation processing to generate necessary code based on DAO interface.
    Building the project will trigger this process and generate the updated implementation of your DAO.
     */

    @Delete
    void delete(Note note);


    // when using LiveData can only use list
    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();


    // use : notation to represent parsed data
    @Query("SELECT * FROM notes WHERE notes.id = :id")
    Note getNoteById(int id);

}
