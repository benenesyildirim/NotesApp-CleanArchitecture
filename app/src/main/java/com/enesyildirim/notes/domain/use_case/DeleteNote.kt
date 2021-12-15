package com.enesyildirim.notes.domain.use_case

import com.enesyildirim.notes.domain.model.Note
import com.enesyildirim.notes.domain.repository.NotesRepository

class DeleteNote(
    private val repository: NotesRepository
) {

    suspend fun deleteNote(note: Note) {
        repository.deleteNote(note)
    }
}