package com.example.oposiciones

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.oposiciones.data.FlashCard
import com.example.oposiciones.data.FlashCardDatabase
import kotlinx.coroutines.launch

class FlashCardViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = FlashCardDatabase.getDatabase(application).flashCardDao()

    fun addFlashCard(flashCard: FlashCard) {
        viewModelScope.launch {
            dao.insertFlashCard(flashCard)
        }
    }

    fun getFlashCards(callback: (List<FlashCard>) -> Unit) {
        viewModelScope.launch {
            callback(dao.getAllFlashCards())
        }
    }
}
