package com.example.mywordstorage.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.mywordstorage.presentation.navigation.NavHostContainer
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.ui.theme.MyWordStorageTheme
import com.example.mywordstorage.presentation.viewmodel.SharedViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val isDarkTheme by sharedViewModel.isDarkTheme.collectAsState()
            MyWordStorageTheme(
                darkTheme = isDarkTheme!!
            ) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isDarkTheme!!
                val statusBarColor = AppTheme.colors.backgroundColor
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = statusBarColor,
                        darkIcons = useDarkIcons
                    )
                }
                NavHostContainer(
                    navController,
                    sharedViewModel
                )
            }
        }
    }
}