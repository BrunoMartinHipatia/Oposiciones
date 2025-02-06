package com.example.oposiciones.examenes

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oposiciones.ExamenesResultadosViewModel
import com.example.oposiciones.ExamenesResultadosViewModelFactory
import com.example.oposiciones.PreguntasActivity
import com.example.oposiciones.PreguntasGuardadasActivity
import com.example.oposiciones.R // Asegúrate de tener imágenes en res/drawable
import com.example.oposiciones.data.ExamenesResultadosRepository
import com.example.oposiciones.data.ResultadosExamenes

@Composable
fun ExamenesGuardadosListScreen(repository: ExamenesResultadosRepository, nombre: Int) {

    val viewModel: ExamenesResultadosViewModel = viewModel(
        factory = ExamenesResultadosViewModelFactory(repository)
    )

    val resultadosExamenes = viewModel.examenList.collectAsState()

    // Filtrar los resultados según el año
    val filteredResultados = resultadosExamenes.value.filter {
        it.examen.anio == nombre
    }

    // Mostrar la lista en un LazyColumn
    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(resultadosExamenes.value) { resultado ->
            ExamenItem3(resultado)
        }
    }

}
@Composable
fun ExamenItem3(examen: ResultadosExamenes) {
    val context = LocalContext.current

    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                val intent = Intent(context, PreguntasGuardadasActivity::class.java).apply {
                    putExtra("exam", examen)
                }
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Imagen
            Image(
                painter = painterResource(id = R.drawable.opo),
                contentDescription = "Imagen del examen",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            // Información del examen
            Text(
                text = "Año: ${examen.examen.anio}",
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = "Número de Preguntas: ${examen.examen.numeroPreguntas}",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "Aciertos: ${examen.examen.numeroAciertos} | Fallos: ${examen.examen.numeroFallos}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}
