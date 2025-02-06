package com.example.oposiciones.data

import kotlinx.coroutines.flow.Flow

interface ExamenesRepository {

     suspend fun insertResult(result: Examen)
     suspend fun deleteResult(result: Examen)
     suspend fun deleteAllResults()
     fun getSavedResults(): Flow<List<Examen>>
}