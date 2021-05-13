package com.example.simplenotes.notes

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.simplenotes.R
import com.example.simplenotes.database.Note
import com.example.simplenotes.formatTime
import com.example.simplenotes.toggleVisibility

@BindingAdapter("noteTitle")
fun TextView.setNoteTitle(item : Note){
    if (item.title.isEmpty()){
        findViewById<TextView>(R.id.tv_note_title).visibility = View.GONE
    } else{
    text = item.title}
}

@BindingAdapter("noteContent")
fun TextView.setNoteContent(item: Note){
    if (item.noteContent.isEmpty()){
        findViewById<TextView>(R.id.tv_hold_content).visibility = View.GONE
    } else{
        text = item.noteContent}

}

@BindingAdapter("noteTime")
fun TextView.setNoteDate(item: Note){
    text = formatTime(item.time)
}