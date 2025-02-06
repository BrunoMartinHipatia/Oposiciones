package com.example.oposiciones.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Preguntas(
    val id: Int,
    val pregunta: String,
    val opciones: List<String>,
    var respuestaDada: String="",
    val respuestaCorrecta: String,
    val numero: Int
) : Parcelable
