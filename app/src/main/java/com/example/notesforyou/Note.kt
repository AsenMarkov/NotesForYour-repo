package com.example.notesforyou

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey
    @ColumnInfo (name = "id")
    val id: Int,
    @ColumnInfo (name = "title")
    val title: String,
    @ColumnInfo (name = "description")
    val description: String,
    @ColumnInfo (name = "priority")
    val priority:Int
) {
}