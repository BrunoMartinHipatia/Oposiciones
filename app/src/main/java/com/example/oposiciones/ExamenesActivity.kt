package com.example.oposiciones

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.examenes.ExamenContainerListScreen
import com.example.oposiciones.examenes.ExamenListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExamenesActivity : ComponentActivity() {

    @Inject
    lateinit var repository: ExamenesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val nombre = intent.getIntExtra("nombreExamen",-1)
        Log.d("el año", nombre.toString())
        setContent {

            ExamenListScreen( repository = repository, nombre)
        }
    }
}
