package com.example.oposiciones.data

import androidx.room.*

@Dao
interface FlashCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashCard(flashCard: FlashCard)

    @Query("SELECT * FROM flashcards_table")
    suspend fun getAllFlashCards(): List<FlashCard>

    @Query("SELECT * FROM flashcards_table WHERE id = :id")
    suspend fun getFlashCardById(id: Int): FlashCard?

    @Delete
    suspend fun deleteFlashCard(flashCard: FlashCard)
}
