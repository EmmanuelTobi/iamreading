package org.exceptos.iamreading.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val description: String = "",
    val imageUrl: String = "",
    val status: String = BookStatus.WANT_TO_READ.name,
    val totalNotes: Int = 0,
    val totalPages: Int = 0,
    val currentPage: Int = 0
)