package org.exceptos.iamreading.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.exceptos.iamreading.data.model.Stats

@Dao
interface StatsDao {
    @Query("SELECT * FROM stats")
    fun getAll(): List<Stats>

    @Query("SELECT * FROM stats WHERE statsType = :type")
    fun getStatsByType(type: String): Flow<Stats?>

    @Query("UPDATE stats SET statsCount = :value, lastUpdated = :lastUpdated WHERE statsType = :type")
    fun updateStatsByType(type: String, value: Int, lastUpdated: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stats: Stats)

}