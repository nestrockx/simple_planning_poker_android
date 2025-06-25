package com.wegielek.simpleplanningpoker.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

class ScreenUtils {
    companion object {
        @Composable
        fun pxToDp(px: Int): Dp {
            val density = LocalDensity.current
            return with(density) { px.toDp() }
        }
    }
}
