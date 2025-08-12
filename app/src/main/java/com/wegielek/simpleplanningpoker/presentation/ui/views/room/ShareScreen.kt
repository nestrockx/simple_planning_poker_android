package com.wegielek.simpleplanningpoker.presentation.ui.views.room

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.wegielek.simpleplanningpoker.prefs.Preferences

@Composable
fun ShareScreen(onNavigate: () -> Unit) {
    val context: Context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Preferences.getRoomCodeFromStorage(context)?.let { Text(it) }
    }
}
