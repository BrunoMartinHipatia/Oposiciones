package com.example.oposiciones

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.oposiciones.data.ExamenesRepository
import javax.inject.Inject

class ExamenesViewModelFactory @Inject constructor(
    private val repository: ExamenesRepository,
    private val appContext: Context // Acepta el contexto de la aplicación
) : NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExamenesViewModel::class.java)) {
            return ExamenesViewModel(repository, appContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
