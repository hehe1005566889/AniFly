package ink.flybird.anifly.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ink.flybird.anifly.network.types.BangumiDetailPage
import ink.flybird.anifly.network.types.BangumiPlayList
import ink.flybird.anifly.network.types.BangumiPlayListList
import ink.flybird.anifly.network.types.BangumiPlayPage

@Composable
fun AFPlayPageInfo(
    title : String,
    videos : List<BangumiPlayList>,
    action : (uri : String) -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column {
            Text(
                text = title,
                fontSize =  19.sp,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(2.dp))

            AFVideoController(videos = videos, action = action)

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
            )
        }
    }
}

@Composable
@Preview
fun PreviewAFPPI()
{
    val testPage = BangumiPlayPage()
    testPage.Set("江户前的废柴精灵：第06集","")
    val testvids = BangumiPlayListList()
    testvids.Add("第一集", "")
    testvids.Add("第二集", "")
    testvids.Add("第三集", "")
    testvids.Add("第四集", "")
    testvids.Add("第五集", "")

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        AFPlayPageInfo(
            "江户前的废柴精灵：第06集",
            testvids.Get(),
            action = {
                Log.d(":q:", it)
            }
        )
    }
}