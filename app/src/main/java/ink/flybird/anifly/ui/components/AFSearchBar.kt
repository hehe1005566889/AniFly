package ink.flybird.anifly.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AFSearchBar(
    text: String,
    navController: NavController? = null,
    callback: (String) -> Unit
) {

    var state by remember { mutableStateOf(false) }
    var setext by rememberSaveable { mutableStateOf("") }



    if(!state) {

        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController?.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
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

@Deprecated("Same As", ReplaceWith(
    "AFSearchBar(\"Test\", Icons.Default.Settings, { }) { }",
    "androidx.compose.material.icons.Icons",
    "androidx.compose.material.icons.filled.Settings"
)
)
@Preview
@Composable
fun PreviewAFSB() {
    AFSearchBar("Test") {

    }
}

