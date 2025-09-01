package com.wegielek.simpleplanningpoker.presentation.ui.views.bottomnavbar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wegielek.simpleplanningpoker.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val backgroundColor = MaterialTheme.colorScheme.background

    if (Screen.bottomNavItems.any { it.route == currentRoute }) {
        val navbarShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        val navbarBorderWidth = 3.dp
        val navbarBorderColor = Color.Gray
        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            tonalElevation = 8.dp, // shadow
            modifier =
                Modifier
                    .drawBehind {
                        val strokeWidth = navbarBorderWidth.toPx()
                        val outline = navbarShape.createOutline(size, layoutDirection, this)
                        val fullPath =
                            when (outline) {
                                is Outline.Generic -> outline.path
                                is Outline.Rounded ->
                                    Path().apply {
                                        addRoundRect(outline.roundRect)
                                    }
                                is Outline.Rectangle ->
                                    Path().apply {
                                        addRect(outline.rect)
                                    }
                            }

                        // Subtract the bottom edge by removing a thin rect from the bottom
                        val borderPath =
                            Path().apply {
                                op(
                                    fullPath,
                                    Path().apply {
                                        addRect(Rect(0f, size.height - strokeWidth, size.width, size.height))
                                    },
                                    PathOperation.Difference,
                                )
                            }

                        drawPath(
                            path = borderPath,
                            color = navbarBorderColor,
                            style = Stroke(width = strokeWidth),
                        )
                        drawPath(
                            color = backgroundColor,
                            path =
                                Path().apply {
                                    addRoundRect(
                                        RoundRect(
                                            rect = Rect(Offset.Zero, size),
                                            topLeft = CornerRadius(20.dp.toPx(), 20.dp.toPx()),
                                            topRight = CornerRadius(20.dp.toPx(), 20.dp.toPx()),
                                            bottomLeft = CornerRadius(0f, 0f),
                                            bottomRight = CornerRadius(0f, 0f),
                                        ),
                                    )
                                },
                        )
                    }.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        ) {
            Screen.bottomNavItems.forEach { screen ->
                CustomNavigationBarItem(
                    icon = screen.iconProvider(),
                    label = screen.label,
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}
