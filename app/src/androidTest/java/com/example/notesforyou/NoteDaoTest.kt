package com.example.android.roomwordssample

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notesforyou.Note
import com.example.notesforyou.NoteDao
import com.example.notesforyou.NoteRoomDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, NoteRoomDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNote() = runBlocking {
        val note = Note(5,"Title 1", "Description 1",1)
        noteDao.insert(note)
        val allNotes = noteDao.getAllNotes().first()
    }

    @Test
    @Throws(Exception::class)
    fun getAllNotes() = runBlocking {
        val note = Note(5,"Title 1", "Description 1",1)
        noteDao.insert(note)
        val note2 = Note(6,"Title 2", "Description 2",2)
        noteDao.insert(note2)
        val allNotes = noteDao.getAllNotes().first()
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val note = Note(5,"Title 1", "Description 1",1)
        noteDao.insert(note)
        val note2 = Note(6,"Title 2", "Description 2",2)
        noteDao.insert(note2)
        noteDao.deleteAll()
        val allNotes = noteDao.getAllNotes().first()
        assertTrue(allNotes.isEmpty())
    }
}
