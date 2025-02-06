package com.example.oposiciones.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.oposiciones.ExamenConverter
import com.example.oposiciones.data.ExamenesDao
import com.example.oposiciones.data.ExamenesResultadosDao

import com.example.oposiciones.data.ResultadosExamenes

@Database(entities = [ResultadosExamenes::class], version = 1)
@TypeConverters(ExamenConverter::class)
abstract class ResultadosExamenesDatabse : RoomDatabase() {
    abstract val resultadosExamenesDao : ExamenesResultadosDao
}
