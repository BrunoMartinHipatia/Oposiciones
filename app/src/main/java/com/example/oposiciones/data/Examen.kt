package com.example.oposiciones.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "examen_table")
@Parcelize

data class Examen(
    @PrimaryKey
    @ColumnInfo(name = "examen_id")
    val id: Int,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "nombre_preguntas")
    val numeroPreguntas: Int,
    @ColumnInfo(name = "preguntas")

    val preguntas: List<Preguntas>,
    @ColumnInfo(name = "numero_aciertos")
    var numeroAciertos: Int,
    @ColumnInfo(name = "numero_fallos")
    var numeroFallos: Int,
    @ColumnInfo(name = "anio")
val anio: Int
    ): Parcelable
