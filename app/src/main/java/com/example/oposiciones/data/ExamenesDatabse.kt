package com.example.oposiciones.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.oposiciones.Converters

@Database(entities = [Examen::class],version = 1)
@TypeConverters(Converters::class)
abstract class ExamenesDatabse : RoomDatabase() {
    abstract val converterDAO : ExamenesDao
}
