package com.wegielek.simpleplanningpoker.ui.views.bottomnavbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomNavigationBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = Color(0xFF66BB6A),
    unselectedColor: Color = Color.Gray,
    backgroundColor: Color = Color(0xFF000000),
) {
    val iconColor by animateColorAsState(if (selected) selectedColor else unselectedColor)
    val labelColor by animateColorAsState(if (selected) selectedColor else unselectedColor)
    val rippleColor by animateColorAsState(Color(0xFF66BB6A))
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier =
            modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication =
                                ripple(
                                    bounded = false,
                                    color = rippleColor,
                                    radius = 25.dp,
                                ),
                            onClick = onClick,
                        ),
                shape = RoundedCornerShape(10.dp),
                color = Color.Transparent,
                tonalElevation = 0.dp,
            ) {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconColor,
                )
            }
            Text(
                text = label,
                fontSize = 12.sp,
                color = labelColor,
            )
        }
    }
}
