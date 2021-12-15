package com.enesyildirim.notes.data

import com.enesyildirim.notes.data.db.NotesDao
import com.enesyildirim.notes.domain.model.Note
import com.enesyildirim.notes.domain.repository.NotesRepository

class NotesRepositoryImpl(
    private val dao: NotesDao
) : NotesRepository {

    override suspend fun getNotes(): List<Note> = dao.getNotes()

    override suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}