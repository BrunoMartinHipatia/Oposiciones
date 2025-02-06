package com.example.oposiciones

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.platform.app.InstrumentationRegistry
import com.example.oposiciones.data.Examen
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.data.Preguntas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ExamenesViewModel(private val repository: ExamenesRepository, private val appContext: Context) : ViewModel() {

    private val _examenList = MutableStateFlow<List<Examen>>(emptyList())
    val examenList: StateFlow<List<Examen>> = _examenList

    init {
        cargarExamenes()
    }

    private fun cargarExamenes() {
        val json = loadJSONFromAsset(appContext, "JsonPreguntas.json")
        val examenes = json?.let { parseJson(it) }

        examenes?.let {
            viewModelScope.launch {
                Log.d("iterador", it.size.toString())
                _examenList.emit(it)
            }
        }
    }

    fun addExamen(
        id: Int,
        nombre: String,
        numeroPreguntas: Int,
        preguntas: List<Preguntas>,
        numeroAciertos: Int,
        numeroFallos: Int,
        anio: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertResult(
                Examen(
                    id,
                    nombre,
                    numeroPreguntas,
                    preguntas,
                    numeroAciertos,
                    numeroFallos,
                    anio
                )
            )
        }
    }

    fun loadJSONFromAsset(context: android.content.Context, filename: String): String? {
        return try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    private fun parseJson(json: String): List<Examen> {
        return try {
            val gson = com.google.gson.Gson()
            gson.fromJson(json, Array<Examen>::class.java).toList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun removeExamen(item: Examen) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteResult(item)
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllResults()
        }
    }
}