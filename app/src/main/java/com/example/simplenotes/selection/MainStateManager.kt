package com.example.simplenotes.selection

//import android.content.ClipData
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.example.simplenotes.database.Note
//
//class MainStateManager {
//    private val TAG : String = javaClass.simpleName
//    private val _toolbarState : MutableLiveData<ToolbarState> =
//            MutableLiveData(ToolbarState.NormalState)
//
//    private val _selectedNodes = MutableLiveData<ArrayList<Note>>()
//    private val _isMultiSelectionEnabled = MutableLiveData<Boolean>()
//
//    val selectedNodes : LiveData<ArrayList<Note>>
//        get() = _selectedNodes
//    val toolbarState : LiveData<ToolbarState>
//        get() = _toolbarState
//
//    fun setToolbarState(state: ToolbarState) = _toolbarState.postValue(state)
//    fun isMultiSelectionStateActive(): Boolean =  _toolbarState.value == ToolbarState.MultiSelectionState
//
//    val multiSelectionState : LiveData<Boolean>
//        get() = _isMultiSelectionEnabled
//
//    fun addOrRemoveClipFromSelectedList(clipData: Note){
//        var list = _selectedNodes.value
//        if (list == null){
//            list = ArrayList()
//        } else{
//            if (list.contains(clipData)){
//                list.remove(clipData)
//            } else{
//                list.add(clipData)
//            }
//        }
//        _selectedNodes.postValue(list)
//    }
//    fun addAllToSelectedList(clips : ArrayList<Note>){
//        _selectedNodes.postValue(clips)
//    }
//
//    fun clearSelectedList(){
//
//    }
//
//}