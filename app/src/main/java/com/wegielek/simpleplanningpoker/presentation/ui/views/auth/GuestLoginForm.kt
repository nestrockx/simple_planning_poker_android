package com.wegielek.simpleplanningpoker.presentation.ui.views.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AuthViewModel
import com.wegielek.simpleplanningpoker.utils.ScreenUtils.Companion.pxToDp
import kotlin.math.min

@Composable
fun GuestLoginForm(
    viewModel: AuthViewModel = hiltViewModel(),
    onGuestLoginClick: (String) -> Unit,
) {
    val nickname = viewModel.nickname

    val verticalScreenWidth =
        min(
            LocalWindowInfo.current.containerSize.width,
            LocalWindowInfo.current.containerSize.height,
        )
    val horizontalPadding = 24.dp

    Column(
        modifier =
            Modifier
                .width(pxToDp(verticalScreenWidth))
                .padding(horizontalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Register", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = { viewModel.onNicknameChanged(it) },
            label = { Text("Nickname") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                ),
            onClick = { onGuestLoginClick(nickname) },
        ) {
            Text("Register", modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp))
        }
    }
}
