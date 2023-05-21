package ink.flybird.anifly.ui.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ink.flybird.anifly.ui.components.AFScaffold
import ink.flybird.anifly.ui.pages.AFRouteName
import ink.flybird.anifly.ui.pages.common.RouteNavigation
import ink.flybird.anifly.ui.pages.common.RouteNavigation.NAVIGATIONS
import ink.flybird.anifly.ui.pages.common.RouteNavigation.selectID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageView(
    homePageViewModel: HomePageViewModel = hiltViewModel(),
    navController: NavController
) {

    AFScaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                NAVIGATIONS.forEach { rd ->
                    NavigationBarItem(
                        selected =  selectID == rd.label ,
                        onClick = {
                            selectID = rd.label
                            navController.navigate(rd.dest)
                        },
                        icon = { Image(imageVector = rd.icon, contentDescription = rd.label) },
                        label = { Text(text = rd.label) }
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Home",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("${AFRouteName.PlayerPage}/5878-6") }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Open Setting"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Something..."
                        )
                    }
                }
            )
        }
    )
}