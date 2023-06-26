package ink.flybird.anifly.ui.pages.home.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ink.flybird.anifly.ui.components.AFDialog
import ink.flybird.anifly.ui.components.AFSearchBar2
import ink.flybird.anifly.ui.components.AFTabContontroller
import ink.flybird.anifly.ui.pages.AFRouteName
import ink.flybird.anifly.ui.pages.home.main.recommand.RecmView
import ink.flybird.anifly.ui.pages.home.main.today.TodayView
import ink.flybird.anifly.ui.pages.home.search.SearchView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageView(
    navController: NavController
) {
    var setext by rememberSaveable { mutableStateOf("5869") }


    Column {
        AFTabContontroller(pages = mapOf(
            Pair("推荐") {
                       RecmView(navController = navController)
            },
            Pair("排行") {

            },
            Pair("今日") {
                TodayView(navController = navController)
            },
            Pair("测试") {
                Column {
                    TextField(
                        value = setext,
                        onValueChange = { setext = it },
                        label = { Text(text = "ID") })

                    Button(onClick = { navController.navigate("${AFRouteName.PlayerPage}/${setext}") }) {
                        Text(text = "Open Player Page")
                    }

                    Button(onClick = { navController.navigate("${AFRouteName.BangumiDetailPage}/${setext}") }) {
                        Text(text = "Open Detil Page")
                    }
                }
            }
        ))
    }
}