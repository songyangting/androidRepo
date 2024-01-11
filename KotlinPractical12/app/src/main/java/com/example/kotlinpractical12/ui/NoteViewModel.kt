package com.example.kotlinpractical12.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.kotlinpractical12.ui.data.Note
import com.example.kotlinpractical12.ui.data.NoteDatabase
import com.example.kotlinpractical12.ui.data.NotesRepository

class NoteViewModel(application: Application) : ViewModel() {

    // to hold data related to notes that is required for the UI
    // business logic or screen level state holder in the UI layer

    private val notesRepository: NotesRepository

    // Using LiveData to observe changes in the single note
    val note: LiveData<Note?>
    val noteList: LiveData<List<Note>>

    init{
        val noteDatabase = NoteDatabase.getDatabase(application)
        val noteDao = noteDatabase.noteDao()
        notesRepository = NotesRepository(noteDao)
        note = notesRepository.getNoteById(1).asLiveData()
        noteList = notesRepository.getAllNotes().asLiveData()

    }
    // Suspend function to insert a note in the database
    suspend fun insertNote(note: Note) {
        notesRepository.insertNote(note)
    }
    // Suspend function to delete a note from the database
    suspend fun deleteNote(note: Note?) {
        note?.let {
            notesRepository.deleteNote(it)
        }
    }



}