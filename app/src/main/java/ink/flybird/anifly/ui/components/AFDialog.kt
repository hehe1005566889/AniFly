package ink.flybird.anifly.ui.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AFDialog(
    title : String,
    content : String,
    button : String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.background)
                .padding(all = 5.dp)
        ) {
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(bottom = 8.dp, start = 10.dp)
                )

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                ) {
                    Text(
                        content,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 8.dp, start = 10.dp)
                    )
                }
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        button,
                        modifier = Modifier.padding(all = 3.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialog()
{
    AFDialog(title = "test", content = "666999啊啊啊啊啊啊qwq", button = "6") {

    }
}