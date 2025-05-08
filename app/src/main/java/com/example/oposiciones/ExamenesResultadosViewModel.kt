package com.example.oposiciones

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oposiciones.data.Examen
import com.example.oposiciones.data.ExamenesResultadosRepository
import com.example.oposiciones.data.Preguntas
import com.example.oposiciones.data.ResultadosExamenes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExamenesResultadosViewModel(private val repository: ExamenesResultadosRepository) :
    ViewModel() {


    private val _examenList = MutableStateFlow<List<ResultadosExamenes>>(emptyList())
    val examenList: StateFlow<List<ResultadosExamenes>> = _examenList

    init {
        // Aquí cargarías los datos
        viewModelScope.launch {
            repository.getSavedResults().collect {
                _examenList.value = it
            }
        }
    }

        val resultList = repository.getSavedResults()

        fun addResult(examen: Examen) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertResult(ResultadosExamenes(0, examen))
            }
        }

    suspend fun getResultLast(): ResultadosExamenes{
        return withContext(Dispatchers.IO) {
            repository.getLastResult()
        }
    }


        fun clearAll() {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteAllResults()
            }
        }


    }
