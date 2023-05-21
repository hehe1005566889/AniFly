package ink.flybird.anifly.ui.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AFAnimationCard(
    title : String,
    subTitle : String,
    imgUri : String,
    action : () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
       // color = MaterialTheme.colorScheme.primaryContainer,
       // border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .height(150.dp)
            .padding(10.dp)
            .clickable {
                action()
            },
        //shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = title,
                    fontSize =  18.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        action()
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(text = subTitle,
                    modifier = Modifier.clickable {
                        action()
                    }
                )

            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .size(width = 100.dp, height = 140.dp)
                    .clickable {
                        action()
                    }
            ) {
                
                //Image(
                //    painter = painterResource(id = R.drawable.panda),
                //    contentScale = ContentScale.Crop,
                //    contentDescription = null
                //)
            }
        }
    }
}

@Preview
@Composable
fun CardPreview()
{
    LazyColumn()
    {
        for(i in 1..20)
            item {

                AFAnimationCard(title = "江户前的废柴精灵", subTitle = "简介：位於東京月島，擁有400年以上歷史的知名神社「高耳...", imgUri = "", action = {

                })
            }
    }

}