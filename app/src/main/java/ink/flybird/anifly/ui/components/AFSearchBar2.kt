package ink.flybird.anifly.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AFSearchBar2(
    hintText : String = "Search...",
    barButton : ImageVector,
    onBarButtonAction : () -> Unit,
    onBarSearch : (String) -> Unit,
    metionItems : () -> Unit = {},
    onActive : () -> Unit = {},
    t : String? = null
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    if (t != null) {
        text = t
    }

    Box(
        Modifier
            .padding(start = 5.dp, end = 5.dp)
            .semantics { isContainer = true }.zIndex(1f).fillMaxWidth()
    ) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                active = false
                onBarSearch(it)
            },
            active = active,
            onActiveChange = {
                active = it
                if(it)
                    onActive()
            },
            placeholder = { Text(hintText) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { if(active){
                    if(text.isNotEmpty())
                        text = ""
                    else
                        active = !active
                } else onBarButtonAction() }) {
                    if(active) Icon(Icons.Default.Close, contentDescription = null)
                    else Icon(barButton, contentDescription = null)
                } }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                metionItems()
            }
        }
    }
}

@Preview
@Composable
fun PreviewAFSB2()
{
    var text by rememberSaveable { mutableStateOf("") }

    Column {

        Text(text = text)

        AFSearchBar2(barButton = Icons.Default.More, onBarButtonAction = {

        }, onBarSearch = {
            text = it
        })
    }
}