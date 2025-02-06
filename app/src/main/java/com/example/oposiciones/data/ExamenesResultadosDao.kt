package com.example.oposiciones.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamenesResultadosDao  {
    @Insert
    suspend fun insertResult(result: ResultadosExamenes)

    @Delete
    suspend fun deleteResult(result: ResultadosExamenes)

    @Query("DELETE FROM result_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM result_table")
    fun getResults(): Flow<List<ResultadosExamenes>>
}