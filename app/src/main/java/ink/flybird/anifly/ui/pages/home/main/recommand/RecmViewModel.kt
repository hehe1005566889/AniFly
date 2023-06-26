package ink.flybird.anifly.ui.pages.home.main.recommand

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import ink.flybird.anifly.data.module.IODispatcher
import ink.flybird.anifly.network.AniCore
import ink.flybird.anifly.network.types.BangumiItem
import ink.flybird.anifly.network.types.BangumiMap
import ink.flybird.anifly.network.types.BangumiPlayList
import ink.flybird.anifly.network.types.BangumiPlayListList
import ink.flybird.anifly.ui.pages.home.main.today.TodayUIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecmViewModel @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _recmUIState = MutableStateFlow(RecmUIState())
    val recmUIState: StateFlow<RecmUIState> = _recmUIState.asStateFlow()
    private var _syncState = false

    fun syncData(
        navController: NavController
    ) {
        viewModelScope.launch(ioDispatcher)
        {
            if (_syncState)
                return@launch
            _syncState = true

            val map = BangumiMap()
            val result = AniCore.getRecentUpdate(map)
            if(!result)
            {
                Log.e(javaClass.name, "Recm View Fetch Data Faild")
                navController.navigateUp()
            }

            _recmUIState.update {
                it.copy(
                    items = MutableStateFlow(map.get()),
                    done = MutableStateFlow(true)
                )
            }
        }

    }
}

data class RecmUIState(
    val items : Flow<MutableMap<String, MutableList<BangumiItem>>> = emptyFlow(),
    val done : Flow<Boolean> = emptyFlow()
)