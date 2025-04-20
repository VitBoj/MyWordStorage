package com.example.mywordstorage.data.repository

import com.example.mywordstorage.data.local.dao.WordDao
import com.example.mywordstorage.data.local.entities.WordEntity
import com.example.mywordstorage.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao
) : WordRepository {
    override suspend fun addWord(word: WordEntity) = wordDao.insertWord(word)

    override suspend fun deleteWord(id: Long) = wordDao.deleteWord(id)

    override suspend fun updateWord(word: WordEntity) {
        wordDao.updateWord(word)
    }

    override fun getWordsByCategory(catId: Long): Flow<List<WordEntity>> =
        wordDao.getWordsByCategory(catId)

    override fun getWordById(id: Long): Flow<WordEntity?> {
        return wordDao.getWordById(id)
    }
}