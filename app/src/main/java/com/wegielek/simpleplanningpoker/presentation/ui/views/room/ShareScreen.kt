package com.wegielek.simpleplanningpoker.presentation.ui.views.room

import android.content.ClipData
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.wegielek.simpleplanningpoker.R
import com.wegielek.simpleplanningpoker.prefs.Preferences
import com.wegielek.simpleplanningpoker.prefs.Preferences.getRoomCodeFromStorage
import kotlinx.coroutines.launch

@Composable
fun ShareScreen(onNavigate: () -> Unit) {
    val context: Context = LocalContext.current
    val clipboardManager = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val base = MaterialTheme.colorScheme.primary.toArgb()
    val background = MaterialTheme.colorScheme.background.toArgb()
    val blended = ColorUtils.blendARGB(base, background, 0.3f)
    val adjustedColor = Color(blended)

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth().padding(0.dp), horizontalArrangement = Arrangement.Center) {
            Icon(
                painter = painterResource(R.drawable.pokerlogo),
                contentDescription = "Poker logo",
                Modifier.size(size = 100.dp).padding(0.dp),
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            getRoomCodeFromStorage(context)?.let {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Room code", fontSize = 25.sp)
                    Spacer(Modifier.size(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(it, fontSize = 20.sp)
                        IconButton(onClick = {
                            scope.launch {
                                clipboardManager.setClipEntry(
                                    ClipEntry(ClipData.newPlainText("Room code", it)),
                                )
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
                ExtendedFloatingActionButton(
                    onClick = {
                        val sendIntent =
                            Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, it)
                                type = "text/plain"
                            }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                    containerColor = adjustedColor,
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Reveal votes",
                        )
                    },
                    text = { Text("Share") },
                )
            } ?: run {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Join or create a room",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(20.dp),
                    )
                    Button(onClick = { onNavigate() }) { Text("Go") }
                }
            }
        }
    }
}
