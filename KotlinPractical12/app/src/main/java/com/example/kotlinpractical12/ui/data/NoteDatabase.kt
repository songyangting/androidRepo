package com.example.kotlinpractical12.ui.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    // singleton prevent multiple instances

    companion object {
        @Volatile
        private var Instance: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            // if the instance is not null return it, otherwise create a new database instance
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NoteDatabase::class.java,
                    "Note DB").build().also {Instance = it}
            }
        }
    }

}