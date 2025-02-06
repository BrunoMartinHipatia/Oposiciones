package com.example.oposiciones.flashcard

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oposiciones.FlashCardViewModel
import com.example.oposiciones.data.FlashCard

@Composable
fun FlashCardScreen(viewModel: FlashCardViewModel) {
    var selectedScreen by remember { mutableStateOf("menu") }

    when (selectedScreen) {
        "menu" -> MenuFlashCards(
            onCreateFlashCard = { selectedScreen = "crear" },
            onViewFlashCards = { selectedScreen = "ver" }
        )
        "crear" -> CrearFlashCard(onBack = { selectedScreen = "menu" }, viewModel = viewModel)
        "ver" -> VerMisFlashCards(onBack = { selectedScreen = "menu" }, viewModel = viewModel)
    }
}

@Composable
fun MenuFlashCards(onCreateFlashCard: () -> Unit, onViewFlashCards: () -> Unit) {
    Row(modifier = Modifier.padding(16.dp)) {
        TextButton(
            onClick = onCreateFlashCard,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(text = "Crear flashcards", fontSize = 16.sp)
        }
        TextButton(
            onClick = onViewFlashCards
        ) {
            Text(text = "Ver mis flashcards", fontSize = 16.sp)
        }
    }
}

@Composable
fun CrearFlashCard(onBack: () -> Unit, viewModel: FlashCardViewModel) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pantalla para crear Flashcards", fontSize = 18.sp)
        var textoPregunta by rememberSaveable { mutableStateOf("") }
        var textoRespuesta by rememberSaveable { mutableStateOf("") }
        var preguntasRespuestas by remember { mutableStateOf(mutableListOf<Pair<String, String>>()) }
        TextField(
            value = textoPregunta,

            onValueChange = {
                textoPregunta = it
            },
            label = { Text("Escribe la pregunta") }
        )
        TextField(
            value = textoRespuesta,
            onValueChange = {
                textoRespuesta = it
            },
            label = { Text("Escribe la respuesta") }
        )


        Button(onClick = {
            preguntasRespuestas = preguntasRespuestas.toMutableList().apply {
                add(textoPregunta to textoRespuesta)
            }
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Añadir pregunta")
        }
        val flashCard = FlashCard(
            nombre = "Historia",
            preguntasRespuestas = listOf(preguntasRespuestas)

        )

        Button(onClick = {
            viewModel.addFlashCard(flashCard)
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Creara flashcard")
        }
        Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Text("Volver al menú")
        }
    }
}

@Composable
fun VerMisFlashCards(onBack: () -> Unit, viewModel: FlashCardViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pantalla para ver Flashcards", fontSize = 18.sp)



        var flashCards by remember { mutableStateOf<List<FlashCard>>(emptyList()) }
        var currentQuestionIndex by remember { mutableStateOf(0) }
        var currentFlashCard by remember { mutableStateOf<FlashCard?>(null) }
        var mostrarRespuesta by remember { mutableStateOf(false) }
        Log.d("los examenes", flashCards.toString());
        val rotationAngle by animateFloatAsState(
            targetValue = if (mostrarRespuesta) 180f else 0f
        )

        LaunchedEffect(key1 = true) {
            viewModel.getFlashCards {
                flashCards = it

            }
        }


        if (currentFlashCard == null) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(flashCards) { examen ->
                    Log.d("mis exa", examen.toString())
                    Card(
                        onClick = {
                            currentFlashCard = examen  
                        },
                        modifier = Modifier.padding(30.dp)
                    ) {
                        Text(text = examen.nombre, modifier = Modifier.padding(30.dp))
                    }
                }
            }
        }


        Log.d("mis cards",currentFlashCard.toString());
        currentFlashCard?.let { flashCard ->
            val preguntasRespuestas = flashCard.preguntasRespuestas.flatten()

            if (preguntasRespuestas.isNotEmpty() && currentQuestionIndex < preguntasRespuestas.size) {
                val (pregunta, respuesta) = preguntasRespuestas[currentQuestionIndex]

                Column(modifier = Modifier.fillMaxSize()) {

                    // Card con la pregunta y respuesta
                    Card(
                        onClick = { mostrarRespuesta = !mostrarRespuesta },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)  // Ocupa todo el espacio disponible
                            .padding(8.dp)
                            .graphicsLayer { rotationY = rotationAngle },
                        elevation = CardDefaults.cardElevation(7.dp)
                    ) {
                        // Centrar el texto dentro del Card
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center, // Centrar verticalmente
                            horizontalAlignment = Alignment.CenterHorizontally // Centrar horizontalmente
                        ) {
                            Text(
                                text = if (mostrarRespuesta) respuesta else pregunta,
                                fontSize = 80.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,

                                modifier = Modifier.fillMaxWidth()  .graphicsLayer { rotationY = rotationAngle } // Para que respete el centrado horizontal
                            )
                        }
                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(onClick = {
                            if (currentQuestionIndex < preguntasRespuestas.size - 1) {
                                currentQuestionIndex++
                                mostrarRespuesta = false
                            }
                        }) {
                            Text("Siguiente pregunta")
                        }

                        Button(onClick = onBack) {
                            Text("Volver al menú")
                        }
                    }
                }
            }
        }
    }
}
