package ink.flybird.anifly.ui.pages.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor() : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUIState())
    val homeUiState: StateFlow<HomeUIState> = _homeUiState.asStateFlow()

    fun registerPages(pages : List<NavPage>)
    {
        _homeUiState.update {
            it.copy(
                pages = MutableStateFlow(pages),
                id = MutableStateFlow(pages[0].label)
            )
        }

        Log.d(javaClass.name, "On Register Pages, Count ${pages.size}")
    }

    fun setNavController(navController: NavController)
    {
        _homeUiState.update {
            it.copy(
                navController = MutableStateFlow(navController)
            )
        }

        Log.d(javaClass.name, "On NavController Apply")
    }

    fun updateSelectedPage(id : String) : Boolean
    {
        _homeUiState.update {
            it.copy(
                id = MutableStateFlow(id)
            )
        }

        Log.d(javaClass.name, "Navigate To Home View's Page $id")
        return true
    }
}

data class NavPage(
    val icon: ImageVector,
    val label: String,
    val header: @Composable (() -> Unit)? = null,
    val page: @Composable () -> Unit
)

data class HomeUIState(
    val pages : Flow<List<NavPage>> = emptyFlow(),
    val navController: Flow<NavController> = emptyFlow(),

    var id : Flow<String> = emptyFlow()
)