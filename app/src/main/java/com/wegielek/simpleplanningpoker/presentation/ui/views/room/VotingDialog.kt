package com.wegielek.simpleplanningpoker.presentation.ui.views.room

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

// enum class VotingType {
//    DEFAULT, // 1..20
//    FIBONACCI, // 1,2,3,5,8,13,21,34,55
//    TSHIRTS, // XS,S,M,L,XL,XXL
//    POWERS_OF_2, // 1,2,4,8,16,32,64
// }

@Composable
fun VotingDialog(
    onDismiss: () -> Unit,
    onValueSelected: (String) -> Unit,
    votingType: RoomType,
) {
    val options: List<String> =
        when (votingType) {
            RoomType.DEFAULT -> (1..20).map { it.toString() }
            RoomType.FIBONACCI -> listOf(1, 2, 3, 5, 8, 13, 21, 34, 55).map { it.toString() }
            RoomType.TSHIRTS -> listOf("XS", "S", "M", "L", "XL", "XXL")
            RoomType.POWERS -> listOf(1, 2, 4, 8, 16, 32, 64).map { it.toString() }
        }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .width(250.dp),
            ) {
                Text(
                    text = "Choose a value",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.heightIn(max = 250.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(options) { value ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            modifier =
                                Modifier
                                    .clickable {
                                        onValueSelected(value)
                                        onDismiss()
                                    },
                        ) {
                            Box(
                                modifier =
                                    Modifier
                                        .padding(12.dp)
                                        .fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = value,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
