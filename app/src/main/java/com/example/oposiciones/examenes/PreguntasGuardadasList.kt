package com.example.oposiciones.examenes

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oposiciones.ExamenesResultadosViewModel
import com.example.oposiciones.ExamenesResultadosViewModelFactory
import com.example.oposiciones.ExamenesViewModel
import com.example.oposiciones.ExamenesViewModelFactory
import com.example.oposiciones.PreguntasActivity
import com.example.oposiciones.R
import com.example.oposiciones.data.Examen
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.data.ExamenesResultadosRepository
import com.example.oposiciones.data.ResultadosExamenes

@Composable
fun PreguntasGuardadasList(resultadosRepository: ExamenesResultadosRepository, repository: ExamenesRepository, examen: ResultadosExamenes?, viewModelResultados: ExamenesResultadosViewModel = viewModel(factory = ExamenesResultadosViewModelFactory(resultadosRepository))) {

    val context = LocalContext.current
    val viewModel: ExamenesViewModel = viewModel(
        factory = ExamenesViewModelFactory(repository,context)
    )


    val examenes = viewModel.examenList.collectAsState()

    Log.d("los examenes", examenes.toString())
    ExamenGuardadoItem(examen, viewModelResultados)


}

@Composable
fun ExamenGuardadoItem(examen: ResultadosExamenes?, examenes: ExamenesResultadosViewModel) {
    val context = LocalContext.current
    var contador = remember { mutableStateOf(0) }
    var selectedOption = remember { mutableStateOf<String?>(null) }
    val preguntas = examen?.examen?.preguntas ?: emptyList()

    // Se almacena el estado de las respuestas para cada pregunta
    val respuestasGuardadas = examen?.examen?.preguntas?.mapIndexed { index, pregunta ->
        pregunta.respuestaDada // Guardamos la respuesta dada por el usuario
    } ?: emptyList()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        if (contador.value < preguntas.size) {
            val pregunta = preguntas[contador.value]

            Text(
                text = "Pregunta ${contador.value + 1}: ${pregunta.pregunta}",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Establecer la opción seleccionada restaurada al retroceder
            selectedOption.value = respuestasGuardadas.getOrNull(contador.value)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Imagen a la izquierda


                // Opciones de respuesta a la derecha
                Column(
                    modifier = Modifier.padding(start = 16.dp),

                    ) {
                    pregunta.opciones.forEach { opcion ->
                        when {
                            // Respuesta dada y correcta
                            pregunta.respuestaCorrecta == opcion &&
                                    respuestasGuardadas[contador.value] == opcion -> {
                                Text(
                                    color = Color.Green,
                                    text = "- $opcion",
                                    fontSize = 16.sp
                                )
                            }
                            // Respuesta dada pero incorrecta
                            respuestasGuardadas[contador.value] == opcion &&
                                    pregunta.respuestaCorrecta != opcion -> {
                                Text(
                                    color = Color.Red,
                                    text = "- $opcion",
                                    fontSize = 16.sp
                                )
                            }
                            // Respuesta correcta pero no dada
                            pregunta.respuestaCorrecta == opcion -> {
                                Text(
                                    color = Color.Green,
                                    text = "- $opcion",
                                    fontSize = 16.sp
                                )
                            }
                            // Opción neutra (ni respuesta correcta ni dada)
                            else -> {
                                Text(
                                    color = Color.Black,
                                    text = "- $opcion",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
                if (pregunta.imagenResId != null) {
                    Image(
                        painter = painterResource(id = pregunta.imagenResId!!),
                        contentDescription = "Imagen del examen",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 50.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón de retroceder
                if (contador.value > 0) {
                    TextButton(
                        onClick = {
                            contador.value-- // Retroceder sin cambiar la respuesta
                            selectedOption.value = respuestasGuardadas[contador.value]
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Pregunta anterior",
                            fontSize = 16.sp
                        )
                    }
                }

                // Botón de siguiente pregunta
                TextButton(
                    onClick = {
                        examen?.examen?.preguntas?.get(contador.value)?.respuestaDada = selectedOption.value.toString()
                        contador.value++ // Avanzar a la siguiente pregunta
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    if(contador.value== examen?.examen?.numeroPreguntas?.minus(1)){
                        Text(
                            text = "Terminar examen",
                            fontSize = 16.sp,
                            color = if (selectedOption.value != null) Color.Black else Color.Gray
                        )
                    }else{
                        Text(
                            text = "Siguiente pregunta",
                            fontSize = 16.sp,
                            color = if (selectedOption.value != null) Color.Black else Color.Gray
                        )
                    }
                }
            }
        } else {
            val activity = (context as? ComponentActivity)
            activity?.finish()
        }
    }
}
