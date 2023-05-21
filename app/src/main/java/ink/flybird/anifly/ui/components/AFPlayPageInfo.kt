package ink.flybird.anifly.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AFPlayPageInfo(

) {


    Column {
        Text(
            text = "这是一个标题",
            fontSize =  13.sp,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(2.dp))
    }
}

@Composable
@Preview
fun PreviewAFPPI()
{
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        AFPlayPageInfo()
    }
}