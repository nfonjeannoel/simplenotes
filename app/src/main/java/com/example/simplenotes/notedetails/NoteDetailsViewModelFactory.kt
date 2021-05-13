package com.example.simplenotes.notedetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplenotes.database.NotesDao
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class NoteDetailsViewModelFactory(
    private val dataSource : NotesDao,
    private val noteId : Long
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteDetailsViewModel::class.java)){
            return NoteDetailsViewModel(dataSource, noteId) as T
        }
        throw IllegalArgumentException("Unknown view model")
    }
}