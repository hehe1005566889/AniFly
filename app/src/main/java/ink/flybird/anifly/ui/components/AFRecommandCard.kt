package ink.flybird.anifly.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import ink.flybird.anifly.R

@Composable
fun AFRecommandCard(
    title : String,
    subTitle : String,
    imgUri : String,
    uri : String,
    action : (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(9.dp),
        colors = CardDefaults.elevatedCardColors(),
        // color = MaterialTheme.colorScheme.primaryContainer,
        // border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .height(100.dp)
            .padding(2.dp)
            .clickable {
                action(uri)
            },
        //shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                shape = RoundedCornerShape(9.dp),
                modifier = Modifier
                    .size(width = 150.dp, height = 140.dp)
            ) {
                AFAsyncImage(
                    contentScale = ContentScale.Crop,
                    data = imgUri,
                    precision = Precision.AUTOMATIC,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = title,
                    fontSize =  13.sp,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subTitle,
                    fontSize =  13.sp,
                    style = MaterialTheme.typography.titleMedium
                )

            }
        }
    }
}

@Composable
@Preview
fun PreviewAFRC()
{
    LazyColumn{
        for(i in 1..20)
        {
            item {
                AFRecommandCard(title = "江户前的废柴精灵", subTitle = "最新:第7集", imgUri = "http://css.yhdmtu.me/news/2023/04/08/20230408092927980.jpg", "") {

                }
            }
        }
    }
}
