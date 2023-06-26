package ink.flybird.anifly.ui.pages.detil

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ink.flybird.anifly.data.common.createShareUri
import ink.flybird.anifly.ui.components.AFAnimationCard
import ink.flybird.anifly.ui.components.AFDetilPageInfoCard
import ink.flybird.anifly.ui.components.AFRecommandCard
import ink.flybird.anifly.ui.components.AFScaffold
import ink.flybird.anifly.ui.components.AFVideoController
import ink.flybird.anifly.ui.extension.collectAsStateValue
import ink.flybird.anifly.ui.extension.share
import ink.flybird.anifly.ui.pages.AFRouteName
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetilPageView(
    navController: NavController,
    viewModel: DetilPageViewModel = hiltViewModel(),
    id : String
) {

    // [1] ViewModel Sync Data And Init Flow Values
    viewModel.syncData(id, navController)
    val uiState = viewModel.detilUiState.collectAsStateValue()

    val title = uiState.title.collectAsStateValue(initial = "")
    val playList = uiState.playList.collectAsStateValue(initial = emptyList())
    val recommandList = uiState.recommandList.collectAsStateValue(initial = emptyList())

    val syncState = uiState.syncState.collectAsStateValue(initial = false)

    // ========================================
    val context = LocalContext.current;

    if (syncState) {
        AFScaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { context.share(createShareUri(id)); }) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Share This Bangumi"
                            )
                        }
                    }
                )
            }
        ) {
            Column {
                AFDetilPageInfoCard(viewModel)

                Spacer(modifier = Modifier.height(2.dp))

                AFVideoController(
                    videos = playList,
                    modifier = Modifier
                        .padding(start = 6.dp)
                ) {
                    Log.d("playpage", it)
                    navController.navigate(
                        "${AFRouteName.PlayerPage}/${
                            it.replace("/v/", "").replace(".html", "")
                        }"
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp)
                )

                LazyColumn {
                    for (item in recommandList) {
                        item {
                            AFAnimationCard(
                                title = item.title,
                                updateInfo = item.time,
                                subTitle = item.detail,
                                imgUri = item.image,
                                uri = item.url
                            ) {
                                Log.d(javaClass.name, it)
                                navController.navigate(
                                    "${AFRouteName.BangumiDetailPage}/${
                                        it.replace(
                                            "/show/",
                                            ""
                                        ).replace(".html", "")
                                    }"
                                )
                            }
                        }
                    }
                }
            }

        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LinearProgressIndicator(
                modifier = Modifier.semantics(mergeDescendants = true) {}.fillMaxWidth()
            )
        }
    }
}