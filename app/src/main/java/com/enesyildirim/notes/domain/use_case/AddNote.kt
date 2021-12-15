package com.enesyildirim.notes.domain.use_case

import com.enesyildirim.notes.domain.model.NoteException
import com.enesyildirim.notes.domain.model.Note
import com.enesyildirim.notes.domain.repository.NotesRepository

class AddNote(
    private val repository: NotesRepository
) {

    @Throws(NoteException::class)
    suspend fun addNote(note: Note) {
        if(note.title.isBlank()) {
            throw NoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()) {
            throw NoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}