package ink.flybird.anifly.ui.pages.player

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.ReadMore
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.exoplayer2.util.MimeTypes
import ink.flybird.anifly.data.exception.AFPlayerError
import ink.flybird.anifly.data.exception.ErrorReason
import ink.flybird.anifly.ui.components.AFPlayPageInfo
import ink.flybird.anifly.ui.components.AFPlayerViewModel
import ink.flybird.anifly.ui.components.AFRecommandCard
import ink.flybird.anifly.ui.components.AFScaffold
import ink.flybird.anifly.ui.components.AFTabContontroller
import ink.flybird.anifly.ui.components.AFVideoPlayer
import ink.flybird.anifly.ui.components.player.fullScreenState
import ink.flybird.anifly.ui.extension.collectAsStateValue
import ink.flybird.anifly.ui.pages.AFRouteName
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerPageView(
    navController: NavController,
    viewModel: PlayerPageViewModel = hiltViewModel(),
    id : String
) {
    // [1] ViewModel Sync Data And Init Flow Values
    viewModel.syncData(id, navController)
    val uiState = viewModel.playerUiState.collectAsStateValue()

    val title = uiState.title.collectAsStateValue(initial = "Bangumi Title")
    val url = uiState.vedioUri.collectAsStateValue(initial = "")
    val syncState = uiState.syncState.collectAsStateValue(initial = false)
    
    val playList = uiState.playList.collectAsStateValue(initial = emptyList())
    val recommandList = uiState.recommandList.collectAsStateValue(initial = emptyList())

    // ========================================

    // [2] Init Player View Model

    val controller : AFPlayerViewModel = hiltViewModel()

    LaunchedEffect(syncState) {
        launch {
            Log.d(javaClass.name, syncState.toString())
            if(syncState) {

                if(url.isBlank())
                    throw AFPlayerError(ErrorReason.VIDEO_URL_EMPTY, "url is not loadable")

                Log.d(javaClass.name, "On Fetech Done $url")

                if(url.endsWith(".m3u8")) {
                    controller.addMedia(url, MimeTypes.APPLICATION_M3U8)
                }
                if(url.endsWith(".mp4")) {
                    controller.addMedia(url, MimeTypes.APPLICATION_MP4)
                }
            }
        }
    }

    // ===========================

    AFScaffold(
        topBar = {
            if(fullScreenState)
                return@AFScaffold

            TopAppBar(
                title = { Text( title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Filled.ReadMore, contentDescription = "More")
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
            AFVideoPlayer(controller = controller, onFullScreenEnter = {
                viewModel.updateFullScreenState(true)
            }, onFullScreenExit = {
                viewModel.updateFullScreenState(false)
            })
        }

        AFTabContontroller(mapOf(
            Pair("简介") {
                if(!syncState)
                    return@Pair

                AFPlayPageInfo(
                    title = title,
                    videos = playList
                ) {
                    Log.d("playpage", it)
                    navController.navigate("${AFRouteName.PlayerPage}/${it.replace("/v/", "").replace(".html", "")}")
                }

                LazyColumn {
                    for (item in recommandList) {
                        item {
                            AFRecommandCard(item.title, item.time, item.image, item.url) {
                                Log.d(javaClass.name, it)
                                navController.navigate("${AFRouteName.BangumiDetailPage}/${it.replace("/show/", "").replace(".html", "")}")
                            }
                        }
                    }
                }
            },
            Pair("评论") {

                Text(
                    text = "尽情期待线上服务的上线~",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        ))
    }
}