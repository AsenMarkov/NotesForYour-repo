package com.example.notesforyou

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val newNoteActivityRequestCode = 1
    private val editNoteActivityRequestCode = 2

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NoteListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        noteViewModel.allNotes.observe(this) { notes ->
            notes.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            startActivityForResult(intent, newNoteActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val title: String = intentData?.getStringExtra(NewNoteActivity.EXTRA_TITLE).toString()
            val description: String = intentData?.getStringExtra(NewNoteActivity.EXTRA_DESCRIPTION).toString()
            val priority: Int = intentData?.getIntExtra(NewNoteActivity.EXTRA_PRIORITY, 1)!!

            val note = Note(0, title, description, priority)
            noteViewModel.insert(note)

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
            } else if (requestCode == editNoteActivityRequestCode && resultCode == Activity.RESULT_OK ){
                var id: Int = intentData?.getIntExtra(NewNoteActivity.EXTRA_ID, -1)!!

                if (id == 1){
                    Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                    return
                }

            val title: String = intentData.getStringExtra(NewNoteActivity.EXTRA_TITLE).toString()
            val description: String = intentData.getStringExtra(NewNoteActivity.EXTRA_DESCRIPTION).toString()
            val priority: Int = intentData.getIntExtra(NewNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(id, title, description, priority)
            noteViewModel.update(note)

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Note Not Saved", Toast.LENGTH_LONG).show()
        }
    }
}