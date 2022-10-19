package com.example.notesforyou

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notesforyou.NoteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteRoomDatabase::class.java,
                    "note_database"
                )
                .addCallback(NoteDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class NoteDatabaseCallback(private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.noteDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(noteDao: NoteDao) {
            // Deletes all content
            noteDao.deleteAll()

            //Creates new notes
            var note = Note(1,"Title 1", "Description 1", 1)
            noteDao.insert(note)
            note = Note(2,"Title 2", "Description 2", 2)
            noteDao.insert(note)
        }
    }
}