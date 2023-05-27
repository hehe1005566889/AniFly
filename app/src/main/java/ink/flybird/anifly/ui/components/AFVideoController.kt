package ink.flybird.anifly.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ink.flybird.anifly.network.types.BangumiPlayList
import ink.flybird.anifly.network.types.BangumiPlayListList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AFVideoController(
    videos : List<BangumiPlayList>,
    modifier: Modifier = Modifier,
    action : (uri : String) -> Unit
) {

    LazyRow(
        modifier = modifier
    ) {

        for(item in videos)
        {
            item {
                AssistChip(
                    onClick = { action(item.url) },
                    label = { Text(text = item.title) },
                    modifier = Modifier
                        .padding(2.dp)
                        .padding(top = 5.dp)
                )
            }
        }

    }
}