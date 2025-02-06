package com.example.oposiciones

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.oposiciones.data.Examen

class ExamenConverter {
    @TypeConverter
    fun fromExamen(examen: Examen): String {
        return Gson().toJson(examen)
    }

    @TypeConverter
    fun toExamen(json: String): Examen {
        val type = object : TypeToken<Examen>() {}.type
        return Gson().fromJson(json, type)
    }
}
