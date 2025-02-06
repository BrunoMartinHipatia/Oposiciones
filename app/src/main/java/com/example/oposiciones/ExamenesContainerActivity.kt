package com.example.oposiciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.examenes.ExamenContainerListScreen
import com.example.oposiciones.examenes.ExamenListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExamenesContainerActivity : ComponentActivity() {

    @Inject
    lateinit var repository: ExamenesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ExamenContainerListScreen( repository = repository)
        }
    }
}
