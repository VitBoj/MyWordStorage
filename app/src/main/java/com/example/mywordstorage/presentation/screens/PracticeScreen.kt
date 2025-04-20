package com.example.mywordstorage.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.outlined.ChangeCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mywordstorage.presentation.components.buttons.RoundIconButton
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.ui.theme.AppTypography
import com.example.mywordstorage.presentation.ui.theme.Orange
import com.example.mywordstorage.presentation.ui.theme.Turquoise
import com.example.mywordstorage.presentation.viewmodel.SharedViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PracticeScreen(sharedViewModel: SharedViewModel) {
    val filteredWords by sharedViewModel.filteredWords.collectAsState()
    var currentWordIndex by rememberSaveable { mutableIntStateOf(0) }
    var showAnswer by rememberSaveable { mutableStateOf(false) }
    var guessMode by rememberSaveable { mutableStateOf(true) }
    val currentWord =
        filteredWords.getOrNull(currentWordIndex)
    if (currentWord != null) {
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .background(AppTheme.colors.backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            IconButton(
                onClick = { guessMode = !guessMode },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(15.dp)
            ) {
                Icon(
                    Icons.Outlined.ChangeCircle,
                    contentDescription = "change guess mode",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            //-----Show word or translation----
            val word = if (guessMode) currentWord.word else currentWord.translation
            Text(
                text = word,
                style = AppTypography.titleLarge,
                color = AppTheme.colors.textColor,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(15.dp))
            //-----show answer-----
            val translation = if (showAnswer) {
                if (guessMode) currentWord.translation else currentWord.word
            } else ""
            Text(
                text = translation,
                color = Turquoise,
                style = AppTypography.titleMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                //-----navigation prev-----
                IconButton(
                    onClick = {
                        if (currentWordIndex > 0) {
                            currentWordIndex--
                            showAnswer = false
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "previous",
                        tint = AppTheme.colors.textColor
                    )
                }
                //------add/remove from learned------
                RoundIconButton(
                    onClick = {
                        sharedViewModel.markWordAsLearned(
                            currentWord,
                            isLearned = !currentWord.isLearned
                        )
                    },
                    backgroundColor = if (currentWord.isLearned) {
                        AppTheme.colors.negativeButtonColor
                    } else {
                        AppTheme.colors.positiveButtonColor
                    },
                    icon =
                        if (currentWord.isLearned) {
                            Icons.Default.Close
                        } else Icons.Default.Check,
                    contentDescription = "add/remove from learned"
                )
                //------Show Answer------
                RoundIconButton(
                    onClick = {
                        showAnswer = true
                    },
                    backgroundColor = Orange,
                    icon = Icons.Default.QuestionMark,
                    contentDescription = "show answer"
                )
                //------Navigation Next------
                IconButton(
                    onClick = {
                        if (currentWordIndex < filteredWords.size - 1) {
                            currentWordIndex++
                            showAnswer = false
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "next",
                        tint = AppTheme.colors.textColor
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    } else {
        Box(
            modifier = Modifier
                .background(color = AppTheme.colors.backgroundColor)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No words", color = AppTheme.colors.negativeButtonColor, style = AppTypography.titleMedium)
        }
    }
}