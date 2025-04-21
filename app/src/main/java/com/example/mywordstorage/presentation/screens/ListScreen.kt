package com.example.mywordstorage.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mywordstorage.presentation.components.dropdown.CategoryDropdownMenu
import com.example.mywordstorage.presentation.components.SearchView
import com.example.mywordstorage.presentation.components.SelectMenu
import com.example.mywordstorage.presentation.components.buttons.ThemeToggleButton
import com.example.mywordstorage.presentation.components.UnlearnedSwitch
import com.example.mywordstorage.presentation.components.WordListItem
import com.example.mywordstorage.presentation.navigation.Screen
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.viewmodel.SharedViewModel
import com.example.mywordstorage.utils.ICON_BUTTON_SIZE

@Composable
fun ListScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val searchQuery by sharedViewModel.searchQuery.collectAsState()
    val categories by sharedViewModel.categories.collectAsState()
    val selectedCategory by sharedViewModel.selectedCategory.collectAsState()
    val isDarkTheme by sharedViewModel.isDarkTheme.collectAsState()
    val selectedWords by sharedViewModel.selectedWords.collectAsState()
    val showJustUnlearned by sharedViewModel.showJustUnlearned.collectAsState()
    val filteredWords by sharedViewModel.filteredWords.collectAsState()
    var showSelectMenu by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.background(AppTheme.colors.backgroundColor)) {
        Column {
            Column(modifier = Modifier.padding(10.dp)) {
                //-----top bar--------
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(50.dp)
                ) {
                    CategoryDropdownMenu(
                        selectedCategory = selectedCategory,
                        categories = categories,
                        onItemClick = { sharedViewModel.selectCategory(it.id) },
                        onAdd = { sharedViewModel.addCategory(it) },
                        onUpdate = { id, name -> sharedViewModel.updateCategory(id, name) },
                        onDelete = { sharedViewModel.deleteCategory(it) }
                    )
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Gray)
                    )
                    UnlearnedSwitch(checked = showJustUnlearned) {
                        sharedViewModel.setShowJustUnlearned(it)
                    }
                    IconButton(onClick = {
                        navController.navigate(Screen.Practice.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = "practice screen",
                            tint = AppTheme.colors.textColor,
                            modifier = Modifier.size(ICON_BUTTON_SIZE)
                        )
                    }
                    isDarkTheme?.let {
                        ThemeToggleButton(
                            { sharedViewModel.setDarkTheme(!it) }, it
                        )
                    }
                }
                //-----Search--------
                SearchView(
                    query = searchQuery,
                    onQueryChange = sharedViewModel::setSearchQuery
                )
            }
            //-----Select words menu--------
            AnimatedVisibility(visible = showSelectMenu) {
                SelectMenu(
                    selectedCount = selectedWords.size,
                    onDelete = {
                        sharedViewModel.deleteSelectedWords()
                        showSelectMenu = false
                    },
                    onCopy = { sharedViewModel.copySelectedWordsToClipboard(context) },
                    onSelectAll = sharedViewModel::selectAllWords,
                    onClear = sharedViewModel::clearSelectedWords,
                    onClose = {
                        sharedViewModel.clearSelectedWords()
                        showSelectMenu = false
                    }
                )
            }
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.DarkGray
            )
            //-----Word List--------
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredWords) { word ->
                    WordListItem(
                        word = word,
                        onDelete = { sharedViewModel.deleteWord(word.id) },
                        onClick = {
                            if (showSelectMenu) {
                                sharedViewModel.toggleWordSelection(word.id)
                            } else {
                                navController.navigate(Screen.AddEditWord.createRoute(word.id))
                            }
                        },
                        onLongClick = {
                            sharedViewModel.toggleWordSelection(word.id)
                            showSelectMenu = true
                        },
                        isSelected = selectedWords.contains(word.id)
                    )
                }
            }
        }
        //-----Add Word Button--------
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.AddEditWord.createRoute(null))
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(15.dp),
            containerColor = AppTheme.colors.positiveButtonColor
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add new word")
        }
    }
}