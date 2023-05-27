package ink.flybird.anifly.ui.pages.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ink.flybird.anifly.ui.components.AFScaffold
import ink.flybird.anifly.ui.components.AFSearchBar
import ink.flybird.anifly.ui.pages.AFRouteName
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
                            //navController.navigate(rd.dest)
                        },
                        icon = { Image(imageVector = rd.icon, contentDescription = rd.label) },
                        label = { Text(text = rd.label) }
                    )
                }
            }
        },
        topBar = {
           AFSearchBar(
               text = "AniFly",
               icon = Icons.Default.Settings,
               action = {

               }) {
               Log.d("AniFly", it)
           }
        }
    ) {

        var setext by rememberSaveable { mutableStateOf("5869") }

        LazyColumn {
            item {
                TextField(value = setext, onValueChange = { setext = it }, label = { Text(text = "ID") })
            }

            item {
                Button(onClick = { navController.navigate("${AFRouteName.PlayerPage}/${setext}") }) {
                    Text(text = "Open Player Page")
                }
            }

            item {
                Button(onClick = { navController.navigate("${AFRouteName.BangumiDetailPage}/${setext}") }) {
                    Text(text = "Open Detil Page")
                }
            }
        }

    }
}