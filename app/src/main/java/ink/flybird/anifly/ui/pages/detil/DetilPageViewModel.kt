package ink.flybird.anifly.ui.pages.detil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ink.flybird.anifly.data.exception.AFDetilPageError
import ink.flybird.anifly.data.exception.DetilPageReason
import ink.flybird.anifly.network.AniCore
import ink.flybird.anifly.network.types.Bangumi
import ink.flybird.anifly.network.types.BangumiDetail
import ink.flybird.anifly.network.types.BangumiDetailPage
import ink.flybird.anifly.network.types.BangumiList
import ink.flybird.anifly.network.types.BangumiPlayList
import ink.flybird.anifly.network.types.BangumiPlayListList
import ink.flybird.anifly.ui.pages.player.PlayerUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val DETIAL_URL = "http://www.yinghuacd.com/show/{}.html"

class DetilPageViewModel  : ViewModel() {

    private val _detilUiState = MutableStateFlow(DetilPageUIState())
    val detilUiState: StateFlow<DetilPageUIState> = _detilUiState.asStateFlow()
    private var _syncState = false

    fun syncData(
        id : String
    ) {

        viewModelScope.launch()
        {
            if(_syncState)
                return@launch

            val uri = createUri(id)
            AniCore.getPlayList(uri, page, playList, recommandList)

            val pageObj = page.Get()

            _detilUiState.update {
                it.copy(
                    title = MutableStateFlow(pageObj.title),
                    time = MutableStateFlow(pageObj.time),
                    location = MutableStateFlow(pageObj.location),
                    type = MutableStateFlow(pageObj.type),
                    img = MutableStateFlow(pageObj.img),
                    update = MutableStateFlow(pageObj.update),
                    detail = MutableStateFlow(pageObj.detail),

                    playList = MutableStateFlow(playList.Get()),
                    recommandList = MutableStateFlow(recommandList.Get()),

                    syncState = MutableStateFlow(true)
                )
            }

            _syncState = true
        }
    }

    private fun createUri(
        id : String
    ) : String {
        if(id.isBlank())
            throw AFDetilPageError(DetilPageReason.PAGE_ID_IS_NULL_OR_EMPTY, "is is blank or empty")
        return DETIAL_URL.replace("{}", id)
    }

    private val page = BangumiDetailPage()
    private val playList = BangumiPlayListList()
    private val recommandList = BangumiList()
}

data class DetilPageUIState(
    val title : Flow<String> = emptyFlow(),
    val time : Flow<String> = emptyFlow(),
    val location : Flow<String> = emptyFlow(),
    val type : Flow<String> = emptyFlow(),
    val img : Flow<String> = emptyFlow(),
    val update : Flow<String> = emptyFlow(),
    val detail : Flow<String> = emptyFlow(),

    val recommandList : Flow<List<Bangumi>> = emptyFlow(),
    val playList : Flow<List<BangumiPlayList>> = emptyFlow(),

    val syncState : Flow<Boolean> = MutableStateFlow(false)
)