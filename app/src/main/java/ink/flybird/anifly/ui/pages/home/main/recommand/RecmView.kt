package ink.flybird.anifly.ui.pages.home.main.recommand

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ink.flybird.anifly.ui.components.AFUpdateCard
import ink.flybird.anifly.ui.extension.collectAsStateValue
import ink.flybird.anifly.ui.pages.AFRouteName

@Composable
fun RecmView(
    navController: NavController,
    recmViewModel: RecmViewModel = hiltViewModel()
) {

    val state = recmViewModel.recmUIState.collectAsStateValue()
    val results = state.items.collectAsStateValue(initial = emptyMap())
    val isDone = state.done.collectAsStateValue(initial = false)

    recmViewModel.syncData(navController)

    if(isDone) {
        LazyColumn {
            for(akey in results.keys)
            {
                item {
                    Column {
                        Text(text = akey)
                        LazyRow {
                            for (avalue in results[akey]!!) {
                                Log.d("RECM", "$akey - ${avalue.title}")
                                item {
                                    AFUpdateCard(
                                        title = avalue.title,
                                        subTitle = avalue.time,
                                        imgUri = avalue.image,
                                        uri = avalue.url, action = {
                                            Log.d("playpage", it)
                                            navController.navigate(
                                                "${AFRouteName.PlayerPage}/${
                                                    it.replace("/v/", "").replace(".html", "")
                                                }"
                                            )
                                        })
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

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
}