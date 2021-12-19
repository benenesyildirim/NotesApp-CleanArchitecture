package com.enesyildirim.notes.presentation.NoteDetail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.enesyildirim.notes.R
import com.enesyildirim.notes.common.Utils
import com.enesyildirim.notes.databinding.FragmentNoteBinding
import com.enesyildirim.notes.domain.model.Note
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var note: Note
    private var isEditModeEnabled: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)

        observeNote()

        setDeleteButtonListener()
        setEditButtonListener()


        return binding.root
    }

    private fun setEditButtonListener() {
        binding.editNoteIv.setOnClickListener {
            updateNoteView()
        }
    }

    private fun setDeleteButtonListener() {
        binding.deleteNoteIv.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                showDeleteNoteDialog()
            }
        }
    }

    private fun observeNote() {
        with(viewModel) {
            noteLiveData.observe(viewLifecycleOwner, {
                note = it
                binding.note = it
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDeleteNoteDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_delete_note, null)

        val generateBtn = view.findViewById(R.id.generate_btn) as Button
        val cancelBtn = view.findViewById(R.id.cancel_btn) as Button

        generateBtn.setOnClickListener {
            with(viewModel) {
                deleteNote(note)
            }
            dialog.dismiss()
            Navigation.findNavController(binding.root).popBackStack()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun updateNoteView() {
        if (isEditModeEnabled) {
            binding.editNoteIv.setImageResource(R.drawable.done_note)
            binding.noteTitleTv.isFocusableInTouchMode = true
            binding.noteTitleTv.isClickable = true
            binding.noteContentTv.isFocusableInTouchMode = true
            binding.noteContentTv.isClickable = true
            isEditModeEnabled = false

            Toast.makeText(context, "Please click what you want to change.", Toast.LENGTH_SHORT)
                .show()
        } else {
            binding.editNoteIv.setImageResource(R.drawable.edit_note)
            binding.noteTitleTv.isFocusableInTouchMode = false
            binding.noteTitleTv.isClickable = false
            binding.noteContentTv.isFocusableInTouchMode = false
            binding.noteContentTv.isClickable = false

            binding.noteTitleTv.clearFocus()
            binding.noteContentTv.clearFocus()
            isEditModeEnabled = true

            hideKeyboard()

            setNote()
        }
    }

    private fun setNote() {
        with(viewModel) {
            val newNote = Note(
                binding.noteTitleTv.text.toString(),
                binding.noteContentTv.text.toString(),
                Utils().getTimeInMillis(),
                "Edited",
                Utils().getRandomColor(),
                note.id
            )
            updateNote(newNote)
        }
    }

    private fun hideKeyboard(){
        val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager . hideSoftInputFromWindow (binding.root.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}