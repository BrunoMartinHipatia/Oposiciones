package com.example.oposiciones.data

import kotlinx.coroutines.flow.Flow

class ExamenesResultadosRepositoryImpl(private val dao : ExamenesResultadosDao) : ExamenesResultadosRepository {

    override suspend fun insertResult(result: ResultadosExamenes) {
        dao.insertResult(result)
    }

    override suspend fun deleteResult(result: ResultadosExamenes) {
        dao.deleteResult(result)
    }

    override suspend fun deleteAllResults() {
        dao.deleteAll()
    }

    override fun getSavedResults(): Flow<List<ResultadosExamenes>> {
        return dao.getResults()
    }


    override suspend fun getLastResult(): ResultadosExamenes {
        return dao.getLastResult()
    }

}