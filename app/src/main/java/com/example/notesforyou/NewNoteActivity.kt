package com.example.notesforyou

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker

class NewNoteActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        val editTitleView = findViewById<EditText>(R.id.text_view_title)
        val editDescriptionView = findViewById<EditText>(R.id.text_view_description)
        val numberPickerPrio = findViewById<NumberPicker>(R.id.number_picker_priority)

        numberPickerPrio.minValue = 1
        numberPickerPrio.maxValue = 10

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(editTitleView.text) && TextUtils.isEmpty(editDescriptionView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val title = editTitleView.text.toString()
                replyIntent.putExtra(EXTRA_TITLE, title)

                val description = editDescriptionView.text.toString()
                replyIntent.putExtra(EXTRA_DESCRIPTION, description)

                val priority = numberPickerPrio.value
                replyIntent.putExtra(EXTRA_PRIORITY, priority)

                setResult(Activity.RESULT_OK, replyIntent)

            }

            finish()
        }
    }

    companion object {
        const val EXTRA_ID = "com.example.notesforyou.ID"
        const val EXTRA_TITLE = "com.example.notesforyou.TITLE"
        const val EXTRA_DESCRIPTION = "com.example.notesforyou.DESCRIPTION"
        const val EXTRA_PRIORITY = "com.example.notesforyou.PRIORITY"
    }
}