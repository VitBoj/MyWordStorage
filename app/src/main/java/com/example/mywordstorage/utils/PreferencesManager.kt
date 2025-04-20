package com.example.mywordstorage.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
        private val CATEGORY_KEY = longPreferencesKey("selected_category")
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        try {
            context.dataStore.edit { preferences ->
                preferences[DARK_THEME_KEY] = enabled
            }
        } catch (e: Exception) {
            Log.e("setDarkTheme", "ERROR while saving theme->", e)
        }
    }

    suspend fun isDarkTheme(): Boolean {
        val preferences = context.dataStore.data.first()[DARK_THEME_KEY]
        return preferences ?: false
    }

    suspend fun saveSelectedCategory(categoryId: Long) {
        context.dataStore.edit { preferences ->
            preferences[CATEGORY_KEY] = categoryId
        }
    }

    suspend fun getSelectedCategory(): Long {
        val preferences = context.dataStore.data.first()
        return preferences[CATEGORY_KEY] ?: 1L
    }
}
