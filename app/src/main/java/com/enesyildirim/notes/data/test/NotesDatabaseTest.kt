package com.enesyildirim.notes.data.test

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enesyildirim.notes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabaseTest: RoomDatabase() {
    abstract val notesDao: NotesDaoTest

    companion object {
        const val DATABASE_NAME = "notes_test_db"
    }
}