package com.example.simplenotes.notes

import android.app.Application
import android.view.View
import android.widget.TextView
import androidx.lifecycle.*
import com.example.simplenotes.database.Note
import com.example.simplenotes.database.NotesDao
import kotlinx.coroutines.launch

class NotesViewModel(
    private val database : NotesDao,
    application: Application
) : AndroidViewModel(application) {

//    private val _stateManager = MainStateManager()
//    val stateManager : MainStateManager
//        get() = _stateManager

    val allNotes = database.getAllNotes()
//    fun getNoteWithId(id : Long){
//        viewModelScope.launch {
//            getNote(id) ?: return@launch
//        }
//    }

    private suspend fun getNote(id: Long): Note? {
        return database.getNoteWithId(id)
    }
    fun deleteNote(noteId : Long){
        viewModelScope.launch {
            delete(noteId)
        }
    }

    private suspend fun delete(noteId: Long){
        database.getNoteWithId(noteId)?.let { database.delete(it) }
    }

    private val _navigateNoteDetails = MutableLiveData<Long>()
    val navigateNoteDetails : LiveData<Long>
        get() = _navigateNoteDetails

//    fun onNavigateNoteDetails(id : Long){
//        _navigateNoteDetails.value = id
//    }
//    fun onNavigateNoteDetailsComplete(){
//        _navigateNoteDetails.value = null
//    }

    private var _showSnackBarEvent = MutableLiveData<Boolean?>()
    val showSnackBarEvent : LiveData<Boolean?>
        get() = _showSnackBarEvent

//    fun onInsertNote(title : String){
//        viewModelScope.launch {
//            var newNote = Note()
//            newNote.title = title
//            insert(newNote)
//        }
//    }
//    private suspend fun insert(note : Note){
//        database.insert(note)
//    }

    fun onClearAll(){
        viewModelScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        database.clear()
        _showSnackBarEvent.value = true
    }

    fun doneShowingSnackBar(){
        _showSnackBarEvent.value = null
    }
}