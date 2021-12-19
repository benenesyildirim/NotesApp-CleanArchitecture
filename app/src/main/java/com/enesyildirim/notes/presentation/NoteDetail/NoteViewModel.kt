package com.enesyildirim.notes.presentation.NoteDetail

import androidx.lifecycle.*
import com.enesyildirim.notes.common.Constants.NOTE_ID
import com.enesyildirim.notes.domain.model.Note
import com.enesyildirim.notes.domain.model.NoteException
import com.enesyildirim.notes.domain.use_case.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCases: NotesUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<Int>(NOTE_ID)?.let { id ->
            getNote(id)
        }
    }

    private val _noteLiveData = MutableLiveData<Note>()
    val noteLiveData: LiveData<Note> get() = _noteLiveData

    private fun getNote(id: Int) = viewModelScope.launch {
        useCases.getNote.invoke(id).let {
            _noteLiveData.postValue(it)
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        useCases.deleteNote.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        try {
            useCases.addNote.addNote(note)
        } catch (e: NoteException) {
            throw NoteException(e.message!!)
        }
    }
}