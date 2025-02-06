package com.example.oposiciones

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.oposiciones.data.Examen
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.data.ExamenesResultadosRepository
import com.example.oposiciones.examenes.ExamenContainerListScreen
import com.example.oposiciones.examenes.ExamenListScreen
import com.example.oposiciones.examenes.PreguntasList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreguntasActivity : ComponentActivity() {

    @Inject
    lateinit var repository: ExamenesRepository
    @Inject
    lateinit var resultadosRepository: ExamenesResultadosRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val examen: Examen? = intent.getParcelableExtra("examen")

        setContent {

            PreguntasList( resultadosRepository = resultadosRepository , repository = repository, examen)
        }
    }
}
