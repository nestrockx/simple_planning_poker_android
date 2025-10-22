package com.wegielek.simpleplanningpoker.presentation.ui.views.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AuthViewModel
import com.wegielek.simpleplanningpoker.utils.ScreenUtils.pxToDp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun RegistrationForm(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterClick: (String, String, String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }

    val username = viewModel.username
    val nickname = viewModel.nickname
    val password = viewModel.password
    val isPasswordVisible = viewModel.isPasswordVisible

    val verticalScreenWidth =
        min(
            LocalWindowInfo.current.containerSize.width,
            LocalWindowInfo.current.containerSize.height,
        )
    val horizontalPadding = 24.dp

    fun onRegister(
        username: String,
        nickname: String,
        password: String,
    ) {
        loading = true
        onRegisterClick(username, nickname, password)
        scope.launch {
            delay(2000)
            loading = false
        }
    }

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
            value = username,
            onValueChange = { viewModel.onUsernameChanged(it) },
            label = { Text("Username") },
            singleLine = true,
            isError = viewModel.usernameError != null,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
        )

        viewModel.usernameError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = { viewModel.onNicknameChanged(it) },
            label = { Text("Display name") },
            singleLine = true,
            isError = viewModel.nicknameError != null,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
        )

        viewModel.nicknameError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation =
                if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            trailingIcon = {
                val image = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            isError = viewModel.passwordError != null,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
        )

        viewModel.passwordError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!loading) {
            Button(
                onClick = { onRegister(username, nickname, password) },
            ) {
                Text("Register", modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp))
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}
