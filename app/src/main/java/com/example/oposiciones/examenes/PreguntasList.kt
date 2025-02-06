package com.example.oposiciones.examenes

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
fun PreguntasList(resultadosRepository: ExamenesResultadosRepository, repository: ExamenesRepository, examen: Examen?, viewModelResultados: ExamenesResultadosViewModel = viewModel(factory = ExamenesResultadosViewModelFactory(resultadosRepository))) {
    val context = LocalContext.current
    val viewModel: ExamenesViewModel = viewModel(
        factory = ExamenesViewModelFactory(repository, context)
    )


    val examenes = viewModel.examenList.collectAsState()

    Log.d("los examenes", examenes.toString())
    ExamenItem3(examen, viewModelResultados)


}

var respuestasCorrectas = 0
var respuestasIncorrectas = 0

// Mapas para guardar respuestas seleccionadas y verificar si son correctas
val opcionesSeleccionadas = mutableMapOf<Int, String?>()
val respuestasMap = mutableMapOf<Int, Boolean>()

@Composable
fun ExamenItem3(examen: Examen?, examenes: ExamenesResultadosViewModel) {
    val context = LocalContext.current
    var contador = remember { mutableStateOf(0) }
    val preguntas = examen?.preguntas ?: emptyList()
    val selectedOption = remember { mutableStateOf<String?>(null) }
    val activity = (context as? ComponentActivity)

    // Manejo del botón de retroceso
    BackHandler {
        Toast.makeText(context, "Saliendo del examen...", Toast.LENGTH_LONG).show()
        respuestasCorrectas = 0
        respuestasIncorrectas = 0
        opcionesSeleccionadas.clear()
        respuestasMap.clear()
        activity?.finish()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
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

            // Restaurar selección previa
            selectedOption.value = opcionesSeleccionadas[contador.value]

            pregunta.opciones.forEach { opcion ->
                val isSelected = opcion == selectedOption.value

                TextButton(
                    onClick = {
                        selectedOption.value = opcion
                        opcionesSeleccionadas[contador.value] = opcion
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (isSelected) Color.White else Color.Black,
                        containerColor = if (isSelected) Color.Blue else Color.Transparent
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(text = "- $opcion", fontSize = 16.sp)
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
                            contador.value--  // Retrocedemos sin modificar contadores
                            selectedOption.value = opcionesSeleccionadas[contador.value]
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Pregunta anterior", fontSize = 16.sp)
                    }
                }

                // Botón de siguiente pregunta
                TextButton(
                    onClick = {
                        if (selectedOption.value != null) {
                            val respuestaCorrecta = selectedOption.value == pregunta.respuestaCorrecta

                            if (!respuestasMap.containsKey(contador.value)) {
                                if (respuestaCorrecta) respuestasCorrectas++ else respuestasIncorrectas++
                            } else if (respuestasMap[contador.value] != respuestaCorrecta) {
                                if (respuestaCorrecta) {
                                    respuestasCorrectas++
                                    respuestasIncorrectas--
                                } else {
                                    respuestasCorrectas--
                                    respuestasIncorrectas++
                                }
                            }

                            respuestasMap[contador.value] = respuestaCorrecta
                            examen?.preguntas?.get(contador.value)?.respuestaDada = selectedOption.value!!
                            contador.value++
                        }
                    },
                    enabled = selectedOption.value != null,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    if(contador.value== examen?.numeroPreguntas?.minus(1)  ){
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
            Text(
                text = "Se acabó el examen",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Respuestas correctas: $respuestasCorrectas",
                fontSize = 18.sp,
                color = Color.Green,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Respuestas incorrectas: $respuestasIncorrectas",
                fontSize = 18.sp,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )

            examen?.numeroAciertos = respuestasCorrectas
            examen?.numeroFallos = respuestasIncorrectas

            if (examen != null) {
                examenes.addResult(examen)
            }

            respuestasCorrectas = 0
            respuestasIncorrectas = 0
            opcionesSeleccionadas.clear()
            respuestasMap.clear()

            TextButton(
                onClick = {},
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Ver resultados", fontSize = 16.sp)
            }
        }
    }
}
