package com.enesyildirim.notes.presentation.Notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesyildirim.notes.domain.model.NoteException
import com.enesyildirim.notes.domain.model.Note
import com.enesyildirim.notes.domain.use_case.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val useCases: NotesUseCases
): ViewModel() {

    init {
        getNotes()
    }

    private val _notesLiveData = MutableLiveData<List<Note>>()
    val notesLiveData: LiveData<List<Note>> get() = _notesLiveData

    private fun getNotes() = viewModelScope.launch {
        useCases.getNotes().let {
            _notesLiveData.postValue(it)
        }
    }

    fun updateNotes(){
        getNotes()
    }

    fun addNote(note: Note) = viewModelScope.launch {
        try {
            useCases.addNote.addNote(note)
            getNotes()
        }catch (e: NoteException){
            throw NoteException(e.message!!)
        }
    }
    
    fun deleteNote(note: Note) = viewModelScope.launch {
        useCases.deleteNote.deleteNote(note)
        getNotes()
    }
}