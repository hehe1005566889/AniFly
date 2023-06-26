package ink.flybird.anifly.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

data class AFContextMenuItem(
    val Text : String,
    val Icon : ImageVector?,
    val OnClick : () -> Unit
)

@Composable
fun AFContextMenu(
    items : List<AFContextMenuItem>,
    expand : Boolean,
    onDismissRequest : () -> Unit
) {
    DropdownMenu(expanded = expand, onDismissRequest = {
        onDismissRequest()
    }) {
        Column {
            for(item in items) {
                DropdownMenuItem(leadingIcon = {
                    if(item.Icon != null) {
                        Icon(imageVector = item.Icon, contentDescription = item.Text)
                    }
                }, text = {
                    Text(text = item.Text)
                }, onClick = {
                    item.OnClick()
                })
            }
        }
    }
}

@Preview
@Composable
fun PreviewAFCM() {
    var e by remember { mutableStateOf(false) }
    
    Column {

        Button(onClick = { e = true }) {
            Text(text = "Show")
        }
        
        AFContextMenu(items = listOf(
            AFContextMenuItem("Test1", null) { },
            AFContextMenuItem("TestWithIcon", Icons.Default.Home) {}
        ), expand = e) {
            e = false
        }
    }
}