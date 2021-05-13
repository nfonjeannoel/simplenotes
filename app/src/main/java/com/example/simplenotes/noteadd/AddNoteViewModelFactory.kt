package com.example.simplenotes.noteadd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplenotes.database.NotesDao
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class AddNoteViewModelFactory(
    private val dataSource : NotesDao,
    private val application: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNoteViewModel::class.java)){
            return AddNoteViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("unknown viewmodel")
    }
}