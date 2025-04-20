package com.example.mywordstorage.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mywordstorage.presentation.navigation.NavRoutes.WORD_ID_ARG
import com.example.mywordstorage.presentation.screens.ListScreen
import com.example.mywordstorage.presentation.screens.PracticeScreen
import com.example.mywordstorage.presentation.screens.WordAddEditScreen
import com.example.mywordstorage.presentation.viewmodel.SharedViewModel

@Composable
fun NavHostContainer(
    navController: NavHostController,
    sharedViewmodel: SharedViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        composable(Screen.List.route) {
            ListScreen(
                navController = navController,
                sharedViewModel = sharedViewmodel
            )
        }
        composable(
            route = Screen.AddEditWord.route,
            arguments = listOf(
                navArgument(WORD_ID_ARG) {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val wordId: Long = backStackEntry.arguments?.getLong(WORD_ID_ARG) ?: -1L
            WordAddEditScreen(
                wordId = if (wordId == -1L) null else wordId,
                navController = navController,
                sharedViewModel = sharedViewmodel
            )
        }
        composable(Screen.Practice.route) {
            PracticeScreen(sharedViewModel = sharedViewmodel)
        }
    }
}
