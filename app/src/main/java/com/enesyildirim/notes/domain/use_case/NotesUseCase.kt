package com.enesyildirim.notes.domain.use_case

data class NotesUseCases(
    val addNote: AddNote,
    val getNote: GetNote,
    val deleteNote: DeleteNote,
    val getNotes: GetNotes
)