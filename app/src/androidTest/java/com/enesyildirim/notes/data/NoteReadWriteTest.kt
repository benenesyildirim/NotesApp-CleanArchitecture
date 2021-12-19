package com.enesyildirim.notes.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.enesyildirim.notes.data.test.NotesDaoTest
import com.enesyildirim.notes.data.test.NotesDatabaseTest
import com.enesyildirim.notes.domain.model.Note
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteReadWriteTest {
    private lateinit var notesDao: NotesDaoTest
    private lateinit var notesDatabase: NotesDatabaseTest

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        notesDatabase = Room.inMemoryDatabaseBuilder(
            context, NotesDatabaseTest::class.java).build()
        notesDao = notesDatabase.notesDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        notesDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val note = Note(
            "title",
            "content",
            100L,
            11,
            3333
        )
        notesDao.insertNote(note)
        val noteById = notesDao.getNoteById(3333)

        Assert.assertEquals(noteById?.title, "title")
    }
}