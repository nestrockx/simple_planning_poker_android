package com.wegielek.simpleplanningpoker.presentation.ui.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.R
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AccountViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
) {
    val context = LocalContext.current
    val user = viewModel.user.collectAsState()
    val scrollState = rememberScrollState()

    val nickname = viewModel.nicknameField
    val isLoggedOut = viewModel.isLoggedOut

    val base = MaterialTheme.colorScheme.primary.toArgb()
    val background = MaterialTheme.colorScheme.background.toArgb()
    val blended = ColorUtils.blendARGB(base, background, 0.3f)
    val adjustedColor = Color(blended)

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            viewModel.clearLogout()
            onNavigate()
        }
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth().padding(0.dp), horizontalArrangement = Arrangement.Center) {
            Icon(
                painter = painterResource(R.drawable.pokerlogo),
                contentDescription = "Poker logo",
                Modifier.size(size = 100.dp).padding(0.dp),
            )
        }
        Box(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            contentAlignment = Alignment.Center,
        ) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(Modifier.padding(20.dp)) {
                    Column {
                        Text("Username: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.padding(18.dp))
                        Text("Display name: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }

                    Column {
                        user.value?.username?.let { Text(it, fontSize = 20.sp) }
                        Spacer(modifier = Modifier.padding(6.dp))
                        if (!viewModel.isNicknameInEdit) {
                            Spacer(modifier = Modifier.padding(6.dp))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (!viewModel.isNicknameInEdit) {
                                user.value
                                    ?.profile
                                    ?.nickname
                                    ?.let { Text(it, fontSize = 20.sp) }
                            } else {
                                OutlinedTextField(
                                    value = nickname,
                                    onValueChange = { viewModel.onNicknameChanged(it) },
                                    label = { Text("New nickname") },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                            if (!viewModel.isNicknameInEdit) {
                                IconButton(onClick = { viewModel.editNickname(true) }) {
                                    Icon(
                                        imageVector = Icons.Filled.ModeEdit,
                                        contentDescription = "Edit nickname",
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            } else {
                                IconButton(onClick = {
                                    viewModel.editNickname(false)
                                    viewModel.updateNickname()
                                }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = "Confirm nickname edit",
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    text = "Privacy Policy",
                    color = MaterialTheme.colorScheme.primary,
                    modifier =
                        Modifier.clickable {
                            val intent =
                                Intent(
                                    Intent.ACTION_VIEW,
                                    "https://nestrockx.github.io/simpleplanningpokerprivacypolicy.html".toUri(),
                                )
                            context.startActivity(intent)
                        },
                )
            }
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.logout(context)
                },
                containerColor = adjustedColor,
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Reveal votes",
                    )
                },
                text = { Text("Logout") },
            )
        }
    }
}
