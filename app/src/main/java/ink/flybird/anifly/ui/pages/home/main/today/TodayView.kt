package ink.flybird.anifly.ui.pages.home.main.today

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ink.flybird.anifly.ui.extension.collectAsStateValue
import ink.flybird.anifly.ui.pages.AFRouteName

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodayView(
    navController: NavController,
    todayViewModel: TodayViewModel = hiltViewModel()
) {
    val state = todayViewModel.todayUIState.collectAsStateValue()
    val results = state.items.collectAsStateValue(initial = emptyList())
    val isDone = state.done.collectAsStateValue(initial = false)

    todayViewModel.syncData(navController)

    if(isDone) {
        LazyColumn {
            for (i in results) {
                item {
                    ListItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = "Nothing"
                            )
                        },
                        modifier = Modifier.clickable {
                            Log.d(javaClass.name, i.title)
                            navController.navigate(
                                "${AFRouteName.BangumiDetailPage}/${
                                    i.url.replace(
                                        "/show/",
                                        ""
                                    ).replace(".html", "")
                                }"
                            )
                        }
                    ) {
                        Text(text = i.title)
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