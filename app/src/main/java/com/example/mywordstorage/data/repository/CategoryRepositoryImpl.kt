package com.example.mywordstorage.data.repository

import com.example.mywordstorage.data.local.dao.CategoryDao
import com.example.mywordstorage.data.local.entities.CategoryEntity
import com.example.mywordstorage.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()

    override suspend fun getCategoryById(id: Long): CategoryEntity {
        return categoryDao.getCategoryById(id)
    }

    override suspend fun addCategory(category: CategoryEntity): Long =
        categoryDao.insertCategory(category)

    override suspend fun updateCategoryName(categoryId: Long, newName: String) {
        return categoryDao.updateCategoryName(categoryId, newName)
    }

    override suspend fun deleteCategory(id: Long) = categoryDao.deleteCategory(id)
}