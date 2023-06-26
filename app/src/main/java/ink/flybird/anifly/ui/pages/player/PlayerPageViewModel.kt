package ink.flybird.anifly.ui.pages.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val baseVideoURL = "http://www.yinghuavideo.com/v/{}.html"

@HiltViewModel
class PlayerPageViewModel @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _playerUiState = MutableStateFlow(PlayerUIState())
    val playerUiState: StateFlow<PlayerUIState> = _playerUiState.asStateFlow()
    private var _syncState = false

    fun syncData(id: String, navController: NavController)
    {
        viewModelScope.launch(ioDispatcher)
        {
            Log.i(javaClass.name, "On PageSync Call")
            if(_syncState)
                return@launch
            _syncState = true

            val url = createUri(id)
            val result = AniCore.getPlayPage(url = url, page = playPage, result = playList, rs = recommandList)
            if(!result)
            {
                Log.e(javaClass.name, "On Fetch Player Data Faild")
                navController.navigateUp()
            }
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
        }
    }

    fun updateFullScreenState(value : Boolean)
    {
        _playerUiState.update {
            it.copy(
                fullScreen = MutableStateFlow(value)
            )
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

    @Deprecated("No Response", ReplaceWith("PlayerPageViewModel.updateFullScreenState", "value : Boolean"))
    fun updateFullScreenState(value : Boolean)
    {
        fullScreen = MutableStateFlow(value)
        Log.d(javaClass.name, fullScreen.toString())
    }

}