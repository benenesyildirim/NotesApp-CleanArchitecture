package com.enesyildirim.notes.domain.use_case

import com.enesyildirim.notes.domain.model.Note
import com.enesyildirim.notes.domain.repository.NotesRepository

class GetNotes(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(): List<Note> {
        return repository.getNotes()
    }
}