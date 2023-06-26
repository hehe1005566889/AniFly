package ink.flybird.anifly.ui.pages.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import ink.flybird.anifly.ui.components.AFScaffold
import ink.flybird.anifly.ui.extension.collectAsStateValue


@Composable
fun HomeViewPageBase(
    viewModel: HomePageViewModel
) {
    val uiState = viewModel.homeUiState.collectAsStateValue()
    val pages = uiState.pages.collectAsStateValue(initial = emptyList())

}