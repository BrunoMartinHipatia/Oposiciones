package com.example.oposiciones

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.oposiciones.data.ExamenesResultadosRepository
import com.example.oposiciones.examenes.ExamenesGuardadosListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExamenesGuardadosActivity : ComponentActivity() {

    @Inject
    lateinit var repository: ExamenesResultadosRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val nombre = intent.getIntExtra("nombreExamen",-1)
        Log.d("el año", nombre.toString())
        setContent {

            ExamenesGuardadosListScreen( repository = repository, nombre)
        }
    }
}
