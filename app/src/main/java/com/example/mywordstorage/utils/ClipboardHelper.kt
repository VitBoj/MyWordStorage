package com.example.mywordstorage.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.example.mywordstorage.data.local.entities.WordEntity

object ClipboardHelper {
    @SuppressLint("ServiceCast")
    fun copyToClipboard(words: List<WordEntity>, context: Context) {
        if (words.isNotEmpty()) {
            val textToCopy = words.joinToString(separator = ",\n") { word ->
                buildString {
                    append("${word.word} — ${word.translation}")
                }
            }
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Words", textToCopy)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Selected words copied to clipboard", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Please select words to copy.", Toast.LENGTH_SHORT).show()
        }
    }
}