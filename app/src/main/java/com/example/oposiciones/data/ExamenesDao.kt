package com.example.oposiciones.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamenesDao   {
    @Insert
    suspend fun insertResult(result: Examen)

    @Delete
    suspend fun deleteResult(result: Examen)

    @Query("DELETE FROM examen_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM examen_table")
    fun getResults(): Flow<List<Examen>>
}