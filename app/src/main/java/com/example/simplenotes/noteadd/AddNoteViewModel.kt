package com.example.simplenotes.noteadd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplenotes.database.Note
import com.example.simplenotes.database.NotesDao
import com.example.simplenotes.formatTime
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val database : NotesDao,
    application: Application
) : AndroidViewModel(application){

    private val _noteId = MutableLiveData<Long>()
    val noteId : LiveData<Long>
        get() = _noteId


    val noteCreationTime = formatTime(System.currentTimeMillis())

    fun onInsertNote(title : String , content : String){
        viewModelScope.launch {
            var newNote = Note()
            newNote.title = title
            newNote.noteContent = content
            insert(newNote)
            _noteId.value = getLatestNote()
        }
    }

    private suspend fun getLatestNote(): Long? {
        return database.getLatestNote().noteId
    }

    private suspend fun insert(note : Note){
        database.insert(note)
    }
}