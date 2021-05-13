package com.example.simplenotes.notes

import com.example.simplenotes.database.Note

class NoteClickListener(val clickListener: (noteId : Long) -> Unit) {
    fun onClick(note : Note) = clickListener(note.noteId)
}
class NoteDeleteListener(val clickListener: (noteId : Long) -> Unit) {
    fun delete(note : Note) = clickListener(note.noteId)
}
