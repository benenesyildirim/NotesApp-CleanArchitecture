package com.enesyildirim.notes.data.db

import androidx.room.*
import com.enesyildirim.notes.domain.model.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getNotes(): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}