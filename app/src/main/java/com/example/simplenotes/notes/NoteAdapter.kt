package com.example.simplenotes.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotes.database.Note
import com.example.simplenotes.databinding.NoteItemListBinding

class NoteAdapter(val listener: NoteClickListener, val noteDeleteListener: NoteDeleteListener) : ListAdapter<Note, NoteAdapter.NotesViewHolder>(NoteDiffCallback()) {

    class NotesViewHolder private constructor(private val binding: NoteItemListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Note, listener: NoteClickListener, deleteListener: NoteDeleteListener) {
            binding.note = item
            binding.noteDeleteListener = deleteListener
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NotesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemListBinding.inflate(layoutInflater, parent, false)
                return NotesViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener, noteDeleteListener)
    }


}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}