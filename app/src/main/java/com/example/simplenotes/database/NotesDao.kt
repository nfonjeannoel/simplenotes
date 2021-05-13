package com.example.simplenotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {
    @Insert
    suspend fun insert(note : Note)
    @Update
    suspend fun update(note: Note)
    @Query("SELECT * FROM my_notes_database WHERE noteId = :id")
    suspend fun getNoteWithId(id : Long) : Note?
    @Query("DELETE FROM my_notes_database")
    suspend fun clear()
    @Query("SELECT * FROM my_notes_database ORDER BY noteId DESC LIMIT 1")
    suspend fun getLatestNote(): Note
    @Delete
    suspend fun delete(note: Note)
    @Query("SELECT * FROM my_notes_database ORDER BY noteId DESC")
    fun getAllNotes() : LiveData<List<Note>>
}