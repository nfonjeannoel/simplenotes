package com.example.simplenotes.notedetails

import android.app.Application
import androidx.lifecycle.*
import com.example.simplenotes.database.Note
import com.example.simplenotes.database.NotesDao
import com.example.simplenotes.formatTime
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
        dataSource : NotesDao,
        val noteId : Long
) : ViewModel(){
    val database = dataSource
    var currentNote = MutableLiveData<Note?>()
    val timeString = Transformations.map(currentNote){
        it?.let { it1 -> formatTime(it1.time) }
    }

    init {
        getCurrentNote()
    }


//    fun onInsertNote(title : String , content : String){
//        viewModelScope.launch {
//            var newNote = Note()
//            newNote.title = title
//            newNote.noteContent = content
//            insert(newNote)
//        }
//    }
//    private suspend fun insert(note : Note){
//        database.insert(note)
//    }
    private fun getCurrentNote() {
        viewModelScope.launch {
            currentNote.value = getNoteFromDatabase()
        }
    }

    fun updateNote(noteTitle : String, noteContent : String){
        viewModelScope.launch {
            val oldNote = database.getNoteWithId(noteId) ?:return@launch
            oldNote.title = noteTitle
            oldNote.noteContent = noteContent
            update(oldNote)
            currentNote.value = database.getNoteWithId(noteId)
        }
    }

    private suspend fun update(note: Note){
        database.update(note)
    }

    private suspend fun getNoteFromDatabase(): Note? {
        var thisNote = database.getNoteWithId(noteId)
        if (thisNote?.noteContent?.isEmpty()?.and((thisNote?.title?.isEmpty())) == true){
            thisNote = null
        }
        return thisNote
    }

    fun deleteNote(){
        viewModelScope.launch {
            database.getNoteWithId(noteId)?.let { delete(it) }
            currentNote.value = null
        }
    }
    private suspend fun delete(note: Note){
        database.delete(note)
    }
}