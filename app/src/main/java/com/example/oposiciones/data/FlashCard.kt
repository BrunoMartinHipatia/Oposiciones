package com.example.oposiciones.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "flashcards_table")
@Parcelize
data class FlashCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    @ColumnInfo(name = "preguntas_respuestas") val preguntasRespuestas: List<MutableList<Pair<String, String>>>
) : Parcelable
