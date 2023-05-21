package ink.flybird.anifly.ui.pages.player

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.More
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.util.MimeTypes
import ink.flybird.anifly.ui.components.AFPlayerController
import ink.flybird.anifly.ui.components.AFRecommandCard
import ink.flybird.anifly.ui.components.AFScaffold
import ink.flybird.anifly.ui.components.AFVideoPlayer
import ink.flybird.anifly.ui.components.player.AFPlayer
import ink.flybird.anifly.ui.components.player.media.AFMediaItem
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerPageView(
    navController: NavController,
    viewModel: PlayerPageViewModel = hiltViewModel(),
    id : String
) {
    var title by remember { mutableStateOf("PlayerPage") }
    var state by remember { mutableStateOf(false) }
    val controller = AFPlayerController()

    val uriList: MutableList<AFMediaItem> by remember { mutableStateOf(ArrayList()) }

    LaunchedEffect(state) {
        launch {
            if(!state) {
                state = viewModel.syncData(id, state)

                title = viewModel.getTitle()
                Log.d(javaClass.name, "On Fetech Done ${viewModel.getUrl()}")


                controller.setUri(viewModel.getUrl())
                controller.setUp()
            }
        }
    }
    
    Log.d("playerpage", "On Draw")

    AFScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Open Setting"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.More,
                            contentDescription = "Search Something..."
                        )
                    }
                }
            )
        }
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AFVideoPlayer(controller = controller)
        }

        var tabIndex by remember { mutableStateOf(0) }
        val tabs = listOf("简介", "评论")

        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }

        when(tabIndex)
        {
            0 -> {
                LazyColumn {
                    if(state) {
                        for (item in viewModel.getRecommandList()) {
                            item {
                                AFRecommandCard(item.title, item.time, item.image) {
                                    Log.d(javaClass.name, "clicked!")
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}