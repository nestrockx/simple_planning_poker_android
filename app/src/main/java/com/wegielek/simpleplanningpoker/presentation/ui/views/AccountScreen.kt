package com.wegielek.simpleplanningpoker.presentation.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

    LaunchedEffect(viewModel.isLoggedOut) {
        if (viewModel.isLoggedOut) {
            viewModel.clearLogout()
            Preferences.clearToken(context)
            onNavigate()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        user.value?.username?.let { Text("Username: $it") }
        user.value
            ?.profile
            ?.nickname
            ?.let { Text("Nickname: $it") }
        Button(
//            colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.padding(2.dp),
            onClick = { viewModel.logout() },
        ) { Text("Logout") }
    }
}
