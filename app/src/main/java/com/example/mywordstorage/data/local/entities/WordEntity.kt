package com.example.mywordstorage.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mywordstorage.data.local.DatabaseConstants.WORDS_TABLE

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_DEFAULT
        )
    ], tableName = WORDS_TABLE, indices = [Index(value = ["categoryId"])]
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val word: String,
    val translation: String,
    val transcription: String,
    val example: String,
    @ColumnInfo(defaultValue = "1")
    val categoryId: Long,
    val isLearned: Boolean
)
