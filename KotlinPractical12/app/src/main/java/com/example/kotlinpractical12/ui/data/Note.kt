package com.example.kotlinpractical12.ui.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note")
data class Note(var title: String, var content: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}




