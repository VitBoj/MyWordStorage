package com.example.mywordstorage.domain.repository

import com.example.mywordstorage.data.local.entities.WordEntity
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun addWord(word: WordEntity)
    suspend fun deleteWord(id: Long)
    fun getWordsByCategory(catId:Long): Flow<List<WordEntity>>
    fun getWordById(id: Long): Flow<WordEntity?>
    suspend fun updateWord(word: WordEntity)
}