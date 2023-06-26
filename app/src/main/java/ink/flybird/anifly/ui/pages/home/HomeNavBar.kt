package ink.flybird.anifly.ui.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ink.flybird.anifly.ui.extension.collectAsStateValue

@Composable
fun HomeNavBar(
    viewModel: HomePageViewModel,
    onClickAction : (Int) -> Unit
) {
    val model = viewModel

    val uiState = viewModel.homeUiState.collectAsStateValue()
    val pages = uiState.pages.collectAsStateValue(initial = emptyList())

    val id = uiState.id.collectAsStateValue(initial = "")

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        pages.forEach { rd ->
            NavigationBarItem(
                selected =  id == rd.label ,
                onClick = {
                    viewModel.updateSelectedPage(rd.label)
                    onClickAction(pages.indexOf(rd))
                },
                icon = { Image(imageVector = rd.icon, contentDescription = rd.label) },
                label = { Text(text = rd.label) }
            ) }
    }
}