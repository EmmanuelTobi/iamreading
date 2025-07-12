package org.exceptos.iamreading.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_notes")
data class BookNotes (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    val bookName: String,
    val noteFromPage: Int,
    val dateAdded: String,
    val dateModified: String,
    val noteImage: String
)