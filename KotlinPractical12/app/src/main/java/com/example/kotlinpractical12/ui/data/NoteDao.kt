package com.example.kotlinpractical12.ui.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note WHERE id == :noteid")
    fun getNoteById(noteid: Int): Flow<Note>

    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<Note>>

}