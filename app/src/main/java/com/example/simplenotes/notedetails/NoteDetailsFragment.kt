package com.example.simplenotes.notedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simplenotes.R
import com.example.simplenotes.database.NotesDao
import com.example.simplenotes.database.NotesDatabase
import com.example.simplenotes.databinding.FragmentNoteDetailsBinding
import com.example.simplenotes.noteadd.AddNoteFragmentDirections
import com.example.simplenotes.noteadd.AddNoteViewModel
import com.example.simplenotes.notes.NotesFragmentDirections

class NoteDetailsFragment : Fragment() {
    private lateinit var viewModel: NoteDetailsViewModel
    private lateinit var binding : FragmentNoteDetailsBinding
    private lateinit var args: NoteDetailsFragmentArgs
    private lateinit var dataSource : NotesDao
    private lateinit var textToShare : String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false)
        val application = requireNotNull(this.activity).application
        dataSource = NotesDatabase.getInstance(application).notesDao
        binding.etNoteContent.clearFocus()
        binding.etTitleNote.clearFocus()

        args = NoteDetailsFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = NoteDetailsViewModelFactory(dataSource, args.noteId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteDetailsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.noteDetailsViewModel = viewModel
        hideKeyboard()
       remakeOptionsMenu()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun remakeOptionsMenu() {
        binding.etTitleNote.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                requireActivity().invalidateOptionsMenu()
            }
        }
        binding.etNoteContent.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                requireActivity().invalidateOptionsMenu()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if (binding.etTitleNote.hasFocus() or binding.etNoteContent.hasFocus()){
            inflater.inflate(R.menu.save_menu, menu)
        } else{
            inflater.inflate(R.menu.details_menu, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mi_delete_note -> {
                viewModel.deleteNote()
                findNavController().navigate(NoteDetailsFragmentDirections.actionNoteDetailsFragmentToNotesFragment())
            }
            R.id.mi_share -> shareSuccess()
            R.id.mi_save -> saveNote()
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onPause() {
//        super.onPause()
//        saveNote()
//    }

    private fun saveNote() {
        val title = binding.etTitleNote.text.toString()
        val content = binding.etNoteContent.text.toString()
        if (title.trim().isEmpty() and content.trim().isEmpty()) {
            Toast.makeText(activity, "empty note", Toast.LENGTH_LONG).show()
//            viewModel.deleteNote()
//            findNavController().navigate(NoteDetailsFragmentDirections.actionNoteDetailsFragmentToNotesFragment())
        } else {
            viewModel.updateNote(title, content)
            Toast.makeText(activity, "note updated", Toast.LENGTH_LONG).show()
            hideKeyboard()
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
        remakeOptionsMenu()
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
        remakeOptionsMenu()
    }


    private fun hideKeyboard(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        binding.etNoteContent.clearFocus()
        binding.etTitleNote.clearFocus()
        requireActivity().invalidateOptionsMenu()
        remakeOptionsMenu()
    }

    private fun getShareIntent() : Intent{

        var shareIntent = Intent(Intent.ACTION_SEND)
        textToShare = when {
            viewModel.currentNote.value?.title?.isEmpty() == true -> {
                viewModel.currentNote.value!!.noteContent
            }
            viewModel.currentNote.value?.noteContent?.isEmpty() == true -> {
                viewModel.currentNote.value!!.title
            }
            else -> {
                 viewModel.currentNote.value!!.title + "  " + viewModel.currentNote.value?.noteContent.toString()
            }
        }
        shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, "$textToShare")
        return shareIntent
    }

    private fun shareSuccess(){
        startActivity(getShareIntent())
        hideKeyboard()
    }



}