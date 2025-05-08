package com.example.oposiciones.examenes

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oposiciones.ExamenesActivity
import com.example.oposiciones.ExamenesViewModel
import com.example.oposiciones.ExamenesViewModelFactory
import com.example.oposiciones.R // Asegúrate de tener imágenes en res/drawable
import com.example.oposiciones.data.Examen
import com.example.oposiciones.data.ExamenesRepository

@Composable
fun ExamenContainerListScreen(repository: ExamenesRepository) {
    // Usa la Factory para crear el ViewModel


    // Contenedor con scroll vertical adicional
    ContenedorConScrollVertical()
}

@Composable
fun ContenedorConScrollVertical() {
    val scrollState = rememberScrollState()
    val list = (2006..2024).toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        list.forEach { year ->

            ContenedorItem(examen = year)
        }
    }
}

val list = arrayListOf<Int>()



@Composable
fun ContenedorItem(examen: Int) {
    val context = LocalContext.current

    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { // Manejo del clic
                Log.d("nombreExamen",examen.toString())
                val intent = Intent(context, ExamenesActivity::class.java).apply {
                    putExtra("nombreExamen", examen)
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

            // Imagen (reemplaza `R.drawable.example_image` con tu recurso)
            Image(
                painter = painterResource(id = R.drawable.opo), // Cambia esto al ID de tu imagen
                contentDescription = "Imagen del examen",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            // Nombre del examen
            Text(
                text = "$examen",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


}
