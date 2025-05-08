package com.example.oposiciones.examenes

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oposiciones.ExamenesResultadosViewModel
import com.example.oposiciones.ExamenesResultadosViewModelFactory
import com.example.oposiciones.ExamenesViewModel
import com.example.oposiciones.ExamenesViewModelFactory
import com.example.oposiciones.R
import com.example.oposiciones.data.Examen
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.data.ExamenesResultadosRepository

lateinit var tipoExamen: String

var puntuacion: Double = 0.0
@Composable
fun ExaminateListScreen(
    resultadosRepository: ExamenesResultadosRepository,
    repository: ExamenesRepository,
    viewModelResultados: ExamenesResultadosViewModel = viewModel(
        factory = ExamenesResultadosViewModelFactory(resultadosRepository)
    )
) {
    val context = LocalContext.current
    val viewModel: ExamenesViewModel = viewModel(
        factory = ExamenesViewModelFactory(repository, context)
    )

    var examenSeleccionado by remember { mutableStateOf<Examen?>(null) }
    var mostrarOpciones by remember { mutableStateOf(true) }  // Estado para mostrar u ocultar las Cards

    if (mostrarOpciones) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                onClick = {
                    tipoExamen = "practico"
                    examenSeleccionado = viewModel.examenRandomTeorico()
                    mostrarOpciones = false  // Ocultar las opciones
                },
                modifier = Modifier.size(160.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.opo),
                        contentDescription = "Imagen Examenes",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Examen teórico",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Card(
                onClick = {
                    tipoExamen = "teorico"
                    examenSeleccionado = viewModel.examenRandomPractica()
                    mostrarOpciones = false  // Ocultar las opciones
                },
                modifier = Modifier.size(160.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.opo),
                        contentDescription = "Imagen Examenes",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Examen práctico",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }

    // Mostrar ExamenItem4 si hay un examen seleccionado
    examenSeleccionado?.let { examen ->
        ExamenItem4(examen, viewModelResultados, viewModel)
    }
}
var ambosExamenesAcabados = false
@Composable
fun ExamenItem4(examen: Examen?, examenes: ExamenesResultadosViewModel, viewModel: ExamenesViewModel) {
    val context = LocalContext.current
    var contador = remember { mutableStateOf(0) }
    val preguntas = examen?.preguntas ?: emptyList()
    val selectedOption = remember { mutableStateOf<String?>(null) }
    var examenFinalizado by remember { mutableStateOf(false) }
    var examenActual by remember { mutableStateOf(examen) }

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
                if (contador.value > 0) {
                    TextButton(
                        onClick = {
                            contador.value--
                            selectedOption.value = opcionesSeleccionadas[contador.value]
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Pregunta anterior", fontSize = 16.sp)
                    }
                }

                TextButton(
                    onClick = {
                        if (selectedOption.value != null) {
                            val respuestaCorrecta = selectedOption.value == pregunta.respuestaCorrecta
                            if (!respuestasMap.containsKey(contador.value)) {
                                if (respuestaCorrecta){
                                    respuestasCorrectas++
                                    puntuacion += 1
                                    Log.d("la puntuacion", puntuacion.toString())
                                }   else{
                                    respuestasIncorrectas++
                                    puntuacion -= 1.33
                                    Log.d("la puntuacion", puntuacion.toString())
                                }
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
                    if (contador.value == examen?.numeroPreguntas?.minus(1)) {
                        Text(text = "Terminar examen", fontSize = 16.sp)
                    } else {
                        Text(text = "Siguiente pregunta", fontSize = 16.sp)
                    }
                }
            }
        } else {
            // Examen finalizado
            examenFinalizado = true

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
            val calculo: Double = 10.0   / examenActual?.numeroPreguntas!!
            Log.d("calculo",calculo.toString());


           val resultado = puntuacion*calculo
            Log.d("resultado",resultado.toString());
            Text(
                text = "Puntuacion: $resultado",
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

            if(!ambosExamenesAcabados){
                ambosExamenesAcabados = true
                TextButton(
                    onClick = {
                        // Recargar un examen práctico
                        examenActual = viewModel.examenRandomPractica()
                        contador.value = 0
                        examenFinalizado = false
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Iniciar examen $tipoExamen", fontSize = 16.sp)
                }
            }else{
                TextButton(
                    onClick = {},
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Ver resultados", fontSize = 16.sp)
                }
            }

        }
    }
}
