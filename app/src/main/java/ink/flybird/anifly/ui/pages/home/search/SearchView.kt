package ink.flybird.anifly.ui.pages.home.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ink.flybird.anifly.ui.components.AFAnimationCard
import ink.flybird.anifly.ui.components.AFScaffold
import ink.flybird.anifly.ui.components.AFSearchBar
import ink.flybird.anifly.ui.components.AFTabContontroller
import ink.flybird.anifly.ui.extension.collectAsStateValue
import ink.flybird.anifly.ui.pages.AFRouteName

@Composable
fun SearchView(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController,
    keyword : String
) {
    viewModel.syncData(keyword)


    val uiState = viewModel.searchUiState.collectAsStateValue()
    val results = uiState.results.collectAsStateValue(initial = listOf())
    val isDone = uiState.queueState.collectAsStateValue(initial = false)

    AFScaffold(
        topBar = {
            //MainPageHeaderView(navController = navController, text = "别点我，我就是摆设，没用的")
            AFSearchBar(text = "搜索 $keyword", navController = navController) {

            }
        }
    ) {

        AFTabContontroller(pages = mapOf(
            Pair("动漫") {
                if(isDone) {
                    LazyColumn {
                        for (item in results) {
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
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .semantics(mergeDescendants = true) {}
                                .fillMaxWidth()
                        )
                    }
                }
            },
            Pair("漫画") {

            },
            Pair("帖子") {

            },
            Pair("专栏") {

            }
        ))

    }
}