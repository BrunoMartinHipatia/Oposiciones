package com.example.oposiciones.data

import kotlinx.coroutines.flow.Flow

interface ExamenesResultadosRepository {

     suspend fun insertResult(result: ResultadosExamenes)
     suspend fun deleteResult(result: ResultadosExamenes)
     suspend fun deleteAllResults()
     fun getSavedResults(): Flow<List<ResultadosExamenes>>
}