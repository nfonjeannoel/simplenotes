package com.example.simplenotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_notes_database")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId : Long = 0L,
    @ColumnInfo(name = "note_title")
    var title : String = "",
    @ColumnInfo(name = "note_content")
    var noteContent : String = "",
    @ColumnInfo(name = "note_time")
    val time : Long = System.currentTimeMillis()
)