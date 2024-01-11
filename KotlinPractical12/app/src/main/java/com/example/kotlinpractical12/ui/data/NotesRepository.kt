package com.example.kotlinpractical12.ui.data

import kotlinx.coroutines.flow.Flow

class NotesRepository(private val noteDao: NoteDao) {

    // Repository class provides a clean API for data access to the rest of the application
    // best practice for code separation and architecture

    // Call the corresponding functions from the NoteDao in this repository class

    suspend fun insertNote(note: Note) = noteDao.insert(note)
    suspend fun deleteNote(note: Note) = noteDao.delete(note)

    fun getAllNotes(): Flow<List<Note>> = noteDao.getAll()
    fun getNoteById(id: Int): Flow<Note> = noteDao.getNoteById(id)
}