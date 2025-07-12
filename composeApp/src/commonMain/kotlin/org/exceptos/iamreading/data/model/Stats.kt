package org.exceptos.iamreading.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stats")
data class Stats (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val statsType: String,
    val statsCount: Int,
    val lastUpdated: String
)