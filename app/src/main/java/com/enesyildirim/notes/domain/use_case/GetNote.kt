package com.enesyildirim.notes.domain.use_case

import com.enesyildirim.notes.domain.model.Note
import com.enesyildirim.notes.domain.repository.NotesRepository

class GetNote(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}