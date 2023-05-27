package ink.flybird.anifly.ui.pages.player

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ink.flybird.anifly.data.exception.AFPlayerError
import ink.flybird.anifly.data.exception.ErrorReason
import ink.flybird.anifly.data.module.IODispatcher
import ink.flybird.anifly.network.AniCore
import ink.flybird.anifly.network.types.Bangumi
import ink.flybird.anifly.network.types.BangumiList
import ink.flybird.anifly.network.types.BangumiPlayList
import ink.flybird.anifly.network.types.BangumiPlayListList
import ink.flybird.anifly.network.types.BangumiPlayPage
import ink.flybird.anifly.ui.extension.collectAsStateValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val baseVideoURL = "http://www.yinghuacd.com/v/{}.html"

@HiltViewModel
class PlayerPageViewModel @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _playerUiState = MutableStateFlow(PlayerUIState())
    val playerUiState: StateFlow<PlayerUIState> = _playerUiState.asStateFlow()
    private var _syncState = false

    fun syncData(id: String)
    {
        viewModelScope.launch()
        {
            if(_syncState)
                return@launch

            val url = createUri(id)
            AniCore.getPlayPage(url = url, page = playPage, result = playList, rs = recommandList)
            Log.d(javaClass.name, "Fetch Detil Page Done")
            Log.d(javaClass.name, "Name : ${playPage.Get().title}")

            _playerUiState.update {
                it.copy(
                    title = MutableStateFlow(playPage.Get().title),
                    vedioUri = MutableStateFlow(playPage.Get().url),

                    syncState = MutableStateFlow(true),

                    playList = MutableStateFlow(playList.Get()),
                    recommandList = MutableStateFlow(recommandList.Get())
                )
            }

            _syncState = true
        }
    }

    private fun createUri(id : String) : String {
        if(id.isBlank())
            throw AFPlayerError(ErrorReason.VIDEO_ID_ERROR, "Id Is Null Or Blank")
        return baseVideoURL.replace("{}", id)
    }

    private val playPage = BangumiPlayPage()
    private val playList = BangumiPlayListList()
    private val recommandList = BangumiList()
}

data class PlayerUIState(
    val title : Flow<String> = emptyFlow(),
    val vedioUri : Flow<String> = emptyFlow(),

    var fullScreen : Flow<Boolean> = MutableStateFlow(false),
    val syncState : Flow<Boolean> = MutableStateFlow(false),

    val recommandList : Flow<List<Bangumi>> = emptyFlow(),
    val playList : Flow<List<BangumiPlayList>> = emptyFlow()
) {

    fun updateFullScreenState(value : Boolean)
    {
        fullScreen = MutableStateFlow(value)
    }

}