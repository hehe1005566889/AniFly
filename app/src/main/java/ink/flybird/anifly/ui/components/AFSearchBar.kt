package ink.flybird.anifly.ui.components

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ink.flybird.anifly.ui.pages.AFRouteName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AFSearchBar(
    text : String,
    icon : ImageVector,
    action : () -> Unit,
    callback : (String) -> Unit
) {

    var state by remember { mutableStateOf(false) }
    var setext by rememberSaveable { mutableStateOf("") }

    if(!state) {

        TopAppBar(
            title = {
                Text(
                    text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = {
                Row {
                    IconButton(onClick = { state = !state }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Something..."
                        )
                    }
                    IconButton(onClick = action) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "A Icon"
                        )
                    }
                }
            }
        )

    } else {


        TopAppBar(
            title = {
                TextField (
                    value = setext,
                    onValueChange = { setext = it },
                    label = { Text("Search ...") },
                    shape = RoundedCornerShape(0.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color(0,0,0,0)),
                )
            },
            navigationIcon = {
                IconButton(onClick = { state = !state }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    callback(setext)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "A Icon"
                    )
                }
            }
        )

    }
}

@Preview
@Composable
fun PreviewAFSB() {
    AFSearchBar("Test", Icons.Default.Settings, {

    }) {

    }
}

