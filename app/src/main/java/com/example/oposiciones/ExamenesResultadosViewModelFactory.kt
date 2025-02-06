package com.example.oposiciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.data.ExamenesResultadosRepository

import javax.inject.Inject

class ExamenesResultadosViewModelFactory @Inject constructor(private val repository: ExamenesResultadosRepository) : NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ExamenesResultadosViewModel(repository) as T

}

