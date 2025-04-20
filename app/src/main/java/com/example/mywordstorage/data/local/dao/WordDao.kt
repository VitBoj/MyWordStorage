package com.example.mywordstorage.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mywordstorage.data.local.entities.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM words WHERE categoryId = :catId ORDER BY id DESC")
    fun getWordsByCategory(catId: Long): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE id = :id")
    fun getWordById(id: Long): Flow<WordEntity?>

    @Update
    suspend fun updateWord(word: WordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

    @Query("DELETE FROM words WHERE id = :id")
    suspend fun deleteWord(id: Long)
}