package com.enesyildirim.notes.presentation.Notes

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesyildirim.notes.common.Constants.NOTE_ID
import com.enesyildirim.notes.R
import com.enesyildirim.notes.common.Utils
import com.enesyildirim.notes.databinding.FragmentNotesBinding
import com.enesyildirim.notes.domain.model.Note
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater)

        setNotesAdapter(binding.root)
        observeNotes()
        initAddNote()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateNotes()
    }

    private fun setNotesAdapter(view: View) {
        notesAdapter = NotesAdapter({
            val bundle = bundleOf(NOTE_ID to it.id)
            Navigation.findNavController(view)
                .navigate(R.id.action_notesFragment_to_noteFragment, bundle)
        }, {
            showDeleteNoteDialog(it)
        })

        binding.notesRv.setHasFixedSize(true)
        binding.notesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
        binding.notesRv.adapter = notesAdapter
    }

    private fun initAddNote() {
        binding.addNoteIv.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                showAddNoteDialog()
            }
        }
    }

    private fun showDeleteNoteDialog(note: Note) {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.apply {
            setTitle("Are you sure to delete this note?")
            setMessage("Note: ${note.title}")
            setPositiveButton("Delete") { _, _ ->
                deleteNote(note)
            }
            setNegativeButton("No") { _, _ -> }
            show()
        }
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showAddNoteDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_add_note, null)

        dialog.window?.setFeatureDrawableAlpha(0,0)

        val titleEt = view.findViewById(R.id.title_tv) as EditText
        val contentEt = view.findViewById(R.id.content_et) as EditText
        val generateBtn = view.findViewById(R.id.generate_btn) as Button
        val cancelBtn = view.findViewById(R.id.cancel_btn) as Button

        titleEt.imeOptions = EditorInfo.IME_ACTION_NEXT
        contentEt.imeOptions = EditorInfo.IME_ACTION_DONE

        generateBtn.setOnClickListener {
            addNote(
                Note(
                    title = titleEt.text.toString(),
                    content = contentEt.text.toString(),
                    timestamp = Utils().getTimeInMillis(),
                    color = Utils().getRandomColor()
                )
            )
            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun addNote(note: Note) {
        with(viewModel) {
            addNote(note)
        }
        observeNotes()
    }

    private fun deleteNote(note: Note) {
        with(viewModel) {
            deleteNote(note)
        }
        observeNotes()
    }

    private fun observeNotes() {
        with(viewModel) {
            notesLiveData.observe(viewLifecycleOwner, {
                notesAdapter.setNotes(it)
            })
        }
    }
}