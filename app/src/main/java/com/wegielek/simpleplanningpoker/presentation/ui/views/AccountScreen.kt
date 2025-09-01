package com.wegielek.simpleplanningpoker.presentation.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.prefs.Preferences
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AccountViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
) {
    val context = LocalContext.current
    val user = viewModel.user.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(viewModel.isLoggedOut) {
        if (viewModel.isLoggedOut) {
            viewModel.clearLogout()
            Preferences.clearToken(context)
            onNavigate()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row {
            Column {
                Text("Username: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(6.dp))
                Text("Nickname: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Column {
                user.value?.username?.let { Text(it, fontSize = 20.sp) }
                Spacer(modifier = Modifier.padding(6.dp))
                Row {
                    user.value
                        ?.profile
                        ?.nickname
                        ?.let { Text(it, fontSize = 20.sp) }
                    Spacer(modifier = Modifier.padding(6.dp))
                    Icon(imageVector = Icons.Default.ModeEdit, contentDescription = "Edit nickname")
                }
            }
        }
        Button(
//            colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.padding(top = 16.dp),
            onClick = { viewModel.logout() },
        ) { Text("Logout") }
    }
}
