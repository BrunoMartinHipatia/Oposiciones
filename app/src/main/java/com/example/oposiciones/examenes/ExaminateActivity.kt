package com.example.oposiciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.oposiciones.data.Examen
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.data.ExamenesResultadosRepository
import com.example.oposiciones.examenes.ExaminateListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExaminateActivity : ComponentActivity() {

    @Inject
    lateinit var repository: ExamenesRepository
    @Inject
    lateinit var resultadosRepository: ExamenesResultadosRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val examen: Examen? = intent.getParcelableExtra("examen")

        setContent {

            ExaminateListScreen( resultadosRepository = resultadosRepository , repository = repository)
        }
    }
}
