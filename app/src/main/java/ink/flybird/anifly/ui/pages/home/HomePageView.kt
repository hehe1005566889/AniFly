package ink.flybird.anifly.ui.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ink.flybird.anifly.ui.components.AFScaffold
import ink.flybird.anifly.ui.extension.collectAsStateValue
import ink.flybird.anifly.ui.pages.home.main.CommunityPageView
import ink.flybird.anifly.ui.pages.home.main.MainPageHeaderView
import ink.flybird.anifly.ui.pages.home.main.MainPageView
import ink.flybird.anifly.ui.pages.home.main.ThemesPageView

@Composable
fun HomePageView(
    homePageViewModel: HomePageViewModel = hiltViewModel(),
    navController: NavController
) {

    homePageViewModel.homeUiState.collectAsStateValue()
    val pages = listOf(
        NavPage(Icons.Default.Home, "主页", header = {
            MainPageHeaderView(navController = navController)
        }) {
            MainPageView(navController = navController)
        },
        NavPage(Icons.Default.Category, "分类") {
            ThemesPageView(navController = navController)
        },
        NavPage(Icons.Default.Chat, "社区") {
            CommunityPageView(navController = navController)
        }
    )

    var cid by rememberSaveable { mutableIntStateOf(0) }
    var id by rememberSaveable { mutableStateOf(pages[0].label) }

    AFScaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                pages.forEach { rd ->
                    NavigationBarItem(
                        selected = id == rd.label,
                        onClick = {
                            id = rd.label
                            cid = (pages.indexOf(rd))
                        },
                        icon = { Image(imageVector = rd.icon, contentDescription = rd.label) },
                        label = { Text(text = rd.label) }
                    )
                }
            }
        },
        content = {
            pages[cid].page()
        },
        topBar = {
            pages[cid].header?.let { it() }
        }
    )
}