package com.wegielek.simpleplanningpoker.presentation.ui.views.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.R
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AuthState
import com.wegielek.simpleplanningpoker.presentation.viewmodels.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val padding: Dp = if (scrollState.maxValue == 0) 0.dp else 12.dp

    LaunchedEffect(viewModel.loginResult) {
        if (viewModel.loginResult) {
            viewModel.clearLoginResult()
            onNavigate()
        }
    }

    LaunchedEffect(viewModel.registerResult) {
        if (viewModel.registerResult) {
            viewModel.clearRegisterResult()
            onNavigate()
        }
    }

    LaunchedEffect(viewModel.guestLoginResult) {
        if (viewModel.guestLoginResult) {
            viewModel.clearGuestLoginResult()
            onNavigate()
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Column(
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .verticalScroll(scrollState)
                    .padding(end = padding)
                    .imePadding(),
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                val drawable: Int =
                    if (isSystemInDarkTheme()) {
                        R.drawable.pokerlogo2
                    } else {
                        R.drawable.pokerlogo2_black
                    }
                Image(
                    painter = painterResource(id = drawable),
                    contentDescription = "Simple poker Logo",
                    modifier = Modifier.size(150.dp),
                    contentScale = ContentScale.Fit,
                )
            }

//            if (viewModel.authState == AuthState.Login || viewModel.authState == AuthState.Register) {
//                Row(
//                    modifier =
//                        Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                24.dp,
//                            ),
//                    horizontalArrangement = Arrangement.Center,
//                ) {
//                    Row(
//                        modifier =
//                            Modifier.wrapContentSize().border(
//                                width = 1.dp,
//                                color = MaterialTheme.colorScheme.onPrimary,
//                                shape = RoundedCornerShape(8.dp),
//                            ),
//                    ) {
//                        if (viewModel.authState == AuthState.Login) {
//                            Box(
//                                modifier =
//                                    Modifier
//                                        .wrapContentSize()
//                                        .clip(RoundedCornerShape(8.dp))
//                                        .background(MaterialTheme.colorScheme.primary),
//                            ) {
//                                Text(
//                                    "Login",
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.Normal,
//                                    modifier = Modifier.padding(30.dp, 12.dp),
//                                )
//                            }
//                            Button(
//                                colors =
//                                    ButtonDefaults.buttonColors(
//                                        containerColor = Color.Transparent,
//                                        contentColor = MaterialTheme.colorScheme.tertiary,
//                                    ),
//                                modifier = Modifier.padding(0.dp),
//                                onClick = { viewModel.setAuthForm(AuthState.Register) },
//                            ) { Text("Register", fontSize = 16.sp, fontWeight = FontWeight.Normal) }
//                        } else if (viewModel.authState == AuthState.Register) {
//                            Button(
//                                colors =
//                                    ButtonDefaults.buttonColors(
//                                        containerColor = Color.Transparent,
//                                        contentColor = MaterialTheme.colorScheme.tertiary,
//                                    ),
//                                modifier = Modifier.padding(0.dp),
//                                onClick = { viewModel.setAuthForm(AuthState.Login) },
//                            ) {
//                                Text(
//                                    "Login",
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.Normal,
//                                    modifier = Modifier.padding(8.dp, 0.dp),
//                                )
//                            }
//                            Box(
//                                modifier =
//                                    Modifier
//                                        .wrapContentSize()
//                                        .clip(RoundedCornerShape(8.dp))
//                                        .background(MaterialTheme.colorScheme.primary),
//                            ) {
//                                Text(
//                                    "Register",
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.Normal,
//                                    modifier = Modifier.padding(22.dp, 12.dp),
//                                )
//                            }
//                        }
//                    }
//                }
//            }
            when (viewModel.authState) {
//                is AuthState.Login -> {
//                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                        LoginForm { username, password ->
//                            Log.d("Login", "Username: $username, Password: $password")
//                            viewModel.login()
//                        }
//                    }
//                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                        Button(
//                            colors =
//                                ButtonDefaults.buttonColors(
//                                    containerColor = Color.Transparent,
//                                    contentColor = MaterialTheme.colorScheme.primary,
//                                ),
//                            onClick = { viewModel.setAuthForm(AuthState.GuestLogin) },
//                            modifier = Modifier.padding(2.dp),
//                        ) {
//                            Text(
//                                "Login as guest",
//                                fontSize = 18.sp,
//                                style =
//                                    TextStyle(textDecoration = TextDecoration.Underline),
//                            )
//                        }
//                    }
//                }
//                is AuthState.Register -> {
//                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                        RegistrationForm { username, nickname, password ->
//                            Log.d(
//                                "Registration",
//                                "Username: $username, Nickname: $nickname, Password: $password",
//                            )
//                            viewModel.register()
//                        }
//                    }
//                }
                is AuthState.GuestLogin -> {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        GuestLoginForm { nickname ->
                            Log.d("Guest login", "Nickname: $nickname")
                            viewModel.guestLogin()
                        }
                    }
//                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                        Button(
//                            colors =
//                                ButtonDefaults.buttonColors(
//                                    containerColor = Color.Transparent,
//                                    contentColor = MaterialTheme.colorScheme.primary,
//                                ),
//                            onClick = { viewModel.setAuthForm(AuthState.Login) },
//                            modifier = Modifier.padding(2.dp),
//                        ) {
//                            Text(
//                                "Go back",
//                                fontSize = 18.sp,
//                                style =
//                                    TextStyle(textDecoration = TextDecoration.Underline),
//                            )
//                        }
//                    }
                }
            }
        }
        CustomVerticalScrollbar(scrollState = scrollState)
    }
}

@Composable
fun CustomVerticalScrollbar(
    scrollState: ScrollState,
    width: Dp = 4.dp,
    color: Color = Color.Gray.copy(alpha = 0.6f),
    cornerRadius: Dp = 2.dp,
) {
    if (scrollState.maxValue == 0) return

    val density = LocalDensity.current
    val containerHeightPx =
        with(density) {
            LocalConfiguration.current.screenHeightDp.dp
                .toPx()
        }
    val scrollY = scrollState.value
    val maxScrollY = scrollState.maxValue

    val thumbHeightPx = (containerHeightPx * containerHeightPx / (containerHeightPx + maxScrollY)).coerceAtLeast(30f)
    val thumbOffsetY = ((containerHeightPx - thumbHeightPx) * scrollY / (maxScrollY.coerceAtLeast(1))).toInt()

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(end = 4.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .width(width)
                    .height(with(density) { thumbHeightPx.toDp() })
                    .offset { IntOffset(x = 0, y = thumbOffsetY) }
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color),
        )
    }
}
