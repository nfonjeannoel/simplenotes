package com.example.simplenotes.noteadd

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simplenotes.R
import com.example.simplenotes.database.NotesDatabase
import com.example.simplenotes.databinding.FragmentAddNoteBinding
import com.example.simplenotes.formatTime


class AddNoteFragment : Fragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var viewModel: AddNoteViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = NotesDatabase.getInstance(application).notesDao

        val viewModelFactory = AddNoteViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddNoteViewModel::class.java)

        binding.addNotesViewModel = viewModel

//        binding.btnNoteDone.setOnClickListener {
//            prepareToSaveNote(viewModel)
//        }
        viewModel.noteId.observe(viewLifecycleOwner, Observer { noteId ->
            findNavController().navigate(AddNoteFragmentDirections
                    .actionAddNoteFragmentToNoteDetailsFragment(noteId))
        })


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mi_save -> prepareToSaveNote()
        }
        return super.onOptionsItemSelected(item)
    }
//    override fun onPause() {
//        super.onPause()
//        saveFromPause()
//    }

//    private fun saveFromPause() {
//        val title = binding.etTitleNote.text.toString()
//        val content = binding.etNoteContent.text.toString()
//        if (title.trim().isEmpty() and content.trim().isEmpty()) {
//            Toast.makeText(activity, "empty note", Toast.LENGTH_LONG).show()
//        } else {
//            viewModel.onInsertNote(title, content)
//            Toast.makeText(activity, "note saved", Toast.LENGTH_LONG).show()
//            hideKeyboard()
//        }
//    }

    private fun prepareToSaveNote() {
        val title = binding.etTitleNote.text.toString()
        val content = binding.etNoteContent.text.toString()
        if (title.trim().isEmpty() and content.trim().isEmpty()) {
            Toast.makeText(activity, "empty note", Toast.LENGTH_LONG).show()
        } else {
            hideKeyboard()
            viewModel.onInsertNote(title, content)
            Toast.makeText(activity, "note added", Toast.LENGTH_LONG).show()
//           findNavController().navigate(AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment())
        }
    }

    private fun hideKeyboard(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        binding.etNoteContent.clearFocus()
        binding.etTitleNote.clearFocus()
    }


}