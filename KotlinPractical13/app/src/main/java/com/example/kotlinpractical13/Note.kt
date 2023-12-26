package com.example.kotlinpractical13

data class Note(
    var noteId: String? = null,
    val title: String,
    val content: String
) {
    constructor() : this(noteId = null, title = "", content = "")
}