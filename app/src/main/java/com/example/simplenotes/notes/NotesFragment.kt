package com.example.simplenotes.notes

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simplenotes.R
import com.example.simplenotes.database.NotesDatabase
import com.example.simplenotes.databinding.FragmentNotesBinding
import com.google.android.material.snackbar.Snackbar

class NotesFragment : Fragment() {
    private lateinit var viewModel: NotesViewModel
    private lateinit var binding: FragmentNotesBinding
//    private lateinit var toolbar: Toolbar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)

//        bindUi()
//        toolbar = Toolbar(activity)
        val application = requireNotNull(this.activity).application
        val dataSource = NotesDatabase.getInstance(application).notesDao

        val viewModelFactory = NotesViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NotesViewModel::class.java)

        val adapter = NoteAdapter(listener =
        NoteClickListener {
                noteId ->

            findNavController().navigate(NotesFragmentDirections
                .actionNotesFragmentToNoteDetailsFragment(noteId))
            Toast.makeText(activity, "tv clicked", Toast.LENGTH_LONG).show()


        },
            noteDeleteListener = NoteDeleteListener { noteId ->
                viewModel.deleteNote(noteId)
                Toast.makeText(activity, "delete btn clicked", Toast.LENGTH_LONG).show()
            }

        )

    binding.noteList.adapter = adapter

        viewModel.allNotes.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.lifecycleOwner = this
        binding.noteViewModel = viewModel


        binding.fabNewNote.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToAddNoteFragment())
        }
        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true){
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "All notes gone",
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackBar()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.li_clear_all -> viewModel.onClearAll()
        }
        return super.onOptionsItemSelected(item)
    }



}