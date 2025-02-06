package com.example.oposiciones.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "result_table")
@Parcelize
data class ResultadosExamenes (@PrimaryKey(autoGenerate = true)
                               @ColumnInfo(name = "result_id")
                               val id : Int,
                               @ColumnInfo(name = "examen")
                               val examen: Examen,



) :Parcelable