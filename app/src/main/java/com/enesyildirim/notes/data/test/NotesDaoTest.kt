package com.enesyildirim.notes.data.test

import androidx.room.*
import com.enesyildirim.notes.domain.model.Note

@Dao
interface NotesDaoTest {

    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getNotes(): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}