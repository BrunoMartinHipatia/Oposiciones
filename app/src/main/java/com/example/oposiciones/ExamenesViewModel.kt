package com.example.oposiciones

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.oposiciones.data.Examen
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.data.Preguntas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.random.Random

class ExamenesViewModel(
    private val repository: ExamenesRepository,
    private val appContext: Context
) : ViewModel() {

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
        anio: Int,
        teorico: String
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
                    anio,
                    teorico
                )
            )
        }
    }
    lateinit var examen: Examen

    lateinit var  preguntas:Preguntas

    fun examenRandomTeorico() :Examen {
        val json = loadJSONFromAsset(appContext, "JsonPreguntas.json")
        val examenes = json?.let { parseJson(it) }

        examenes?.let {
            viewModelScope.launch {
                val preguntasSeleccionadas = mutableListOf<Preguntas>()


                val examenesTeoricos = it.filter { examen -> examen.tipo == "teorico" }

                // Obtener todas las preguntas de los exámenes teóricos
                val todasLasPreguntas = examenesTeoricos.flatMap { examen -> examen.preguntas }

                // Seleccionar 100 preguntas aleatorias sin repetición
                preguntasSeleccionadas.addAll(todasLasPreguntas.shuffled().take(100))

                // Crear el examen con las preguntas seleccionadas
                examen = Examen(
                    id = 0,
                    nombre = "Examen Teórico Aleatorio",
                    numeroPreguntas = preguntasSeleccionadas.size,
                    preguntas = preguntasSeleccionadas,
                    numeroAciertos = 0,
                    numeroFallos = 0,
                    anio = 0,
                    tipo = "teorico"
                )

                Log.d("Examen generado", examen.toString())
            }
        }
        return examen
    }
    fun examenRandomPractica() :Examen {
        val json = loadJSONFromAsset(appContext, "JsonPreguntas.json")
        val examenes = json?.let { parseJson(it) }

        examenes?.let {
            viewModelScope.launch {
                val preguntasSeleccionadas = mutableListOf<Preguntas>()


                val examenesTeoricos = it.filter { examen -> examen.tipo == "practico" }

                // Obtener todas las preguntas de los exámenes teóricos
                val todasLasPreguntas = examenesTeoricos.flatMap { examen -> examen.preguntas }

                // Seleccionar 100 preguntas aleatorias sin repetición
                preguntasSeleccionadas.addAll(todasLasPreguntas.shuffled().take(4))

                // Crear el examen con las preguntas seleccionadas
                examen = Examen(
                    id = 0,
                    nombre = "Examen Teórico Aleatorio",
                    numeroPreguntas = preguntasSeleccionadas.size,
                    preguntas = preguntasSeleccionadas,
                    numeroAciertos = 0,
                    numeroFallos = 0,
                    anio = 0,
                    tipo = "teorico"
                )

                Log.d("Examen generado", examen.toString())
            }
        }
        return examen
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