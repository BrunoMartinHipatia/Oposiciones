package com.example.oposiciones.data

import kotlinx.coroutines.flow.Flow

class ExamenesRepositoryImpl(private val dao : ExamenesDao) : ExamenesRepository {

    override suspend fun insertResult(result: Examen) {
        dao.insertResult(result)
    }

    override suspend fun deleteResult(result: Examen) {
        dao.deleteResult(result)
    }

    override suspend fun deleteAllResults() {
        dao.deleteAll()
    }

    override fun getSavedResults(): Flow<List<Examen>> {
        return dao.getResults()
    }
}