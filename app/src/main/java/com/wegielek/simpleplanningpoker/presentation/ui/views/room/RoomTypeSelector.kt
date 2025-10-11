package com.wegielek.simpleplanningpoker.presentation.ui.views.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun RoomTypeSelector(
    selectedRoomType: RoomType,
    onRoomTypeSelected: (RoomType) -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Choose a room type:")
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // two columns
            modifier = Modifier.heightIn(max = 200.dp), // prevent infinite scroll
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            items(RoomType.entries.size) { index ->
                val type = RoomType.entries[index]
                Row(
                    verticalAlignment = CenterVertically,
                    modifier =
                        Modifier
                            .selectable(
                                selected = type == selectedRoomType,
                                onClick = { onRoomTypeSelected(type) },
                                role = Role.RadioButton,
                            ).padding(10.dp),
                ) {
                    RadioButton(
                        selected = type == selectedRoomType,
                        onClick = null,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    when (type) {
                        RoomType.DEFAULT -> {
                            Text(text = "Default\n1 .. 20")
                        }
                        RoomType.FIBONACCI -> {
                            Text(text = "Fibonacci\n1 2 3 5 8..")
                        }
                        RoomType.TSHIRTS -> {
                            Text(text = "Tshirts\nXS S M L..")
                        }
                        RoomType.POWERS -> {
                            Text(text = "Powers\n2 4 8 16..")
                        }
                    }
                }
            }
        }
    }
}
