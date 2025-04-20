package com.example.mywordstorage.presentation.navigation

import com.example.mywordstorage.presentation.navigation.NavRoutes.ADD_EDIT_WORD
import com.example.mywordstorage.presentation.navigation.NavRoutes.LIST
import com.example.mywordstorage.presentation.navigation.NavRoutes.PRACTICE
import com.example.mywordstorage.presentation.navigation.NavRoutes.WORD_ID_ARG

sealed class Screen(val route: String) {
    data object List : Screen(LIST)
    data object Practice : Screen(PRACTICE)
    data object AddEditWord : Screen("$ADD_EDIT_WORD?$WORD_ID_ARG={$WORD_ID_ARG}") {
        fun createRoute(wordId: Long?): String {
            return if (wordId != null) "$ADD_EDIT_WORD?$WORD_ID_ARG=$wordId" else ADD_EDIT_WORD
        }
    }
}

object NavRoutes {
    const val LIST = "list"
    const val PRACTICE = "practice"
    const val ADD_EDIT_WORD = "add_edit_word"
    const val WORD_ID_ARG = "wordId"
}