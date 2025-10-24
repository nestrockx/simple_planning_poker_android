package com.wegielek.simpleplanningpoker.presentation.ui.views.auth

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AuthViewModel
import com.wegielek.simpleplanningpoker.utils.ScreenUtils.pxToDp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun GuestLoginForm(
    viewModel: AuthViewModel = hiltViewModel(),
    onGuestLoginClick: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val nickname = viewModel.nickname

    val verticalScreenWidth =
        min(
            LocalWindowInfo.current.containerSize.width,
            LocalWindowInfo.current.containerSize.height,
        )
    val horizontalPadding = 24.dp

    fun onGuestLogin(nickname: String) {
        loading = true
        onGuestLoginClick(nickname)
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
        Text("Guest Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.padding(16.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = { viewModel.onNicknameChanged(it) },
            label = { Text("Display name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
        )

        Spacer(Modifier.padding(16.dp))

        if (!loading) {
            Button(
                onClick = { onGuestLogin(nickname) },
            ) {
                Text("Continue", modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp))
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))
        }

        Spacer(Modifier.padding(16.dp))

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
}
