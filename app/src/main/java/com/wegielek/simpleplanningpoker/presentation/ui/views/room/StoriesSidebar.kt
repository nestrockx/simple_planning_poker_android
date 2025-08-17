package com.wegielek.simpleplanningpoker.presentation.ui.views.room

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wegielek.simpleplanningpoker.presentation.viewmodels.RoomViewModel
import kotlinx.coroutines.launch

@Composable
fun StoriesSidebar(
    viewModel: RoomViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val logTag = "StoriesSidebar"

    val context = LocalContext.current

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val room = viewModel.room.collectAsState()
    val stories = viewModel.stories.collectAsState()
    val votes = viewModel.votes.collectAsState()
    val currentStory = viewModel.currentStory

    val density = LocalDensity.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val insets = WindowInsets.ime
    val imeBottom =
        remember {
            derivedStateOf {
                insets.getBottom(density)
            }
        }
    val imeVisible =
        remember {
            derivedStateOf {
                imeBottom.value > 0
            }
        }

    fun onAddStory() {
        viewModel.createStory()
        viewModel.clearNewStoryTitle()
        keyboardController?.hide()
//        viewModel.send()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp),
            ) {
                Column {
                    Text(
                        "Stories",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                    )
                    stories.value?.let { storyList ->
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            itemsIndexed(storyList, key = { index, it -> it.id }) { index, item ->
                                NavigationDrawerItem(
                                    modifier = Modifier.padding(horizontal = 12.dp),
                                    label = { Text(item.title) },
                                    selected = item.id == currentStory?.id,
                                    onClick = {
                                        viewModel.updateCurrentStory(item)
//                                    scope.launch { drawerState.close() }
                                    },
                                    badge = {
                                        if (storyList.size > 1) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                tint = Color(0xFFFF9AA1),
                                                contentDescription = "Remove",
                                                modifier =
                                                    Modifier
                                                        .size(30.dp)
                                                        .clickable {
                                                            Log.d(
                                                                logTag,
                                                                "Remove ${item.title}",
                                                            )
                                                            viewModel.deleteStory(item.id)
                                                        }.padding(4.dp),
                                            )
                                        }
                                    },
                                    colors =
                                        NavigationDrawerItemDefaults.colors(
                                            selectedContainerColor =
                                                MaterialTheme.colorScheme.primary.copy(
                                                    alpha = 0.4f,
                                                ),
//                                        selectedTextColor = MaterialTheme.colorScheme.primary,
//                                        selectedIconColor = MaterialTheme.colorScheme.primary,
//                                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
//                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        ),
                                )
                                if (index < storyList.lastIndex) {
                                    HorizontalDivider(
                                        color = Color.DarkGray,
                                        thickness = 0.5.dp,
                                        modifier = Modifier.padding(horizontal = 34.dp),
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        modifier =
                            Modifier
                                .wrapContentSize()
                                .padding(
                                    bottom =
                                        if (imeVisible.value) {
                                            50.dp
                                        } else {
                                            0.dp
                                        },
                                ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        OutlinedTextField(
                            value = viewModel.newStoryTitle,
                            onValueChange = { viewModel.onNewStoryTitleChanged(it) },
                            label = { Text("New story") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                        )
                        Button(onClick = { onAddStory() }) { Text("Add") }
                    }
                }
            }
        },
    ) {
        content()
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 8.dp)
                        .height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier.size(28.dp),
                    )
                }
                room.value?.name?.let { Text("Room: $it", fontSize = 20.sp) }
                Row(
                    modifier = Modifier.weight(1f).padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    IconButton(onClick = { viewModel.clearRoomCode(context) }) {
                        Icon(
                            Icons.Default.Cancel,
                            contentDescription = "Close",
                            modifier = Modifier.size(28.dp),
                        )
                    }
                }
            }
        }
    }
}
