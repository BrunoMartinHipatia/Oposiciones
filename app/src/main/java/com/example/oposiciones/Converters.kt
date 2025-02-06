package com.example.oposiciones

import androidx.room.TypeConverter
import com.example.oposiciones.data.Preguntas
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromPreguntaList(preguntas: List<Preguntas>?): String {
        return Gson().toJson(preguntas)
    }

    @TypeConverter
    fun toPreguntaList(json: String): List<Preguntas>? {
        val type = object : TypeToken<List<Preguntas>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromMap(value: Map<String, String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toMap(value: String): Map<String, String> {
        val type = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: List<MutableList<Pair<String, String>>>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(data: String): List<MutableList<Pair<String, String>>> {
        val type = object : TypeToken<List<MutableList<Pair<String, String>>>>() {}.type
        return Gson().fromJson(data, type)
    }
}
