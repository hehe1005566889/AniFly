package ink.flybird.anifly.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.size.Precision
import coil.size.Scale
import ink.flybird.anifly.network.types.BangumiDetailPage
import ink.flybird.anifly.ui.extension.collectAsStateValue
import ink.flybird.anifly.ui.pages.detil.DetilPageViewModel

@Composable
fun AFDetilPageInfoCard(
    viewModel: DetilPageViewModel
) {
    // ViewModel Operators
    val uiState = viewModel.detilUiState.collectAsStateValue()

    val detail = uiState.detail.collectAsStateValue(initial = "")
    val img = uiState.img.collectAsStateValue(initial = "")
    val title = uiState.title.collectAsStateValue(initial = "")
    val time = uiState.time.collectAsStateValue(initial = "")
    val type = uiState.type.collectAsStateValue(initial = "")
    val location = uiState.location.collectAsStateValue(initial = "")
    val update = uiState.update.collectAsStateValue(initial = "")

    // ==================

    Surface(
        // color = MaterialTheme.colorScheme.primaryContainer,
        // border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {

        var showDetilDialog by remember { mutableStateOf(false) }

        if (showDetilDialog) {
            AFDialog(title = "介绍...", content = detail, button = "关闭") { showDetilDialog = false }
        }

        Row {

            Surface (
                shape = RoundedCornerShape(9.dp),
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .padding(10.dp)
            ) {
                AFAsyncImage(
                    contentScale = ContentScale.Crop,
                    data = img,
                    scale = Scale.FILL,
                    precision = Precision.INEXACT,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = title,
                    fontSize =  24.sp,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "放送日期 : $time",
                    fontSize =  15.sp,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "动漫分类 : $type",
                    fontSize =  15.sp,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "出品地区 : $location",
                    fontSize =  15.sp,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "最近更新 : $update",
                    fontSize =  15.sp,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "简介 : $detail",
                    fontSize =  15.sp,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .clickable {
                            showDetilDialog = true
                        }
                )

            }
        }
    }
}

@Composable
@Preview
fun PreviewAFDPIC()
{
    //val page = BangumiDetailPage()
    //page.Set("江户前的废柴精灵", "日本", "2023-04-07", "百合 治愈 日常", "http://css.yhdmtu.me/news/2023/04/08/20230408092927980.jpg", "更新至7集", " 《江户前的废柴精灵》位於東京月島，擁有400年以上歷史的知名神社「高耳神社」。其所奉祀的神，是一位從江戶時代時就被召喚到日本，現在卻只會成天待在神社裡打電動看漫畫做模型，還患有社交恐懼症的家裡蹲精靈。 神社家的女兒，同時也是新科巫女的小金井小糸，為了侍奉這位精靈神大人可說是傷透了腦筋。而她心中的憧憬，是在十年前一個大雪紛飛的日子裡，出現在她眼前的一位全身雪白的女性——。")

    //AFDetilPageInfoCard(page)
}