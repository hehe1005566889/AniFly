package ink.flybird.anifly.ui.pages.player

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ink.flybird.anifly.data.exception.AFPlayerError
import ink.flybird.anifly.data.exception.ErrorReason
import ink.flybird.anifly.data.module.IODispatcher
import ink.flybird.anifly.network.AniCore
import ink.flybird.anifly.network.types.Bangumi
import ink.flybird.anifly.network.types.BangumiList
import ink.flybird.anifly.network.types.BangumiPlayListList
import ink.flybird.anifly.network.types.BangumiPlayPage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val baseVideoURL = "http://www.yinghuacd.com/v/{}.html"

@HiltViewModel
class PlayerPageViewModel @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun bindData()
    {

    }

    fun syncData(id : String, state : Boolean) : Boolean
    {
        val url = createUri(id)
        AniCore.getPlayPage(url = url, page = playPage, result = playList, rs = recommandList)
        Log.d(javaClass.name, "Fetch Detil Page Done")
        Log.d(javaClass.name, "Name : ${playPage.Get().title}")

        return true
    }

    private fun createUri(id : String) : String {
        if(id.isBlank())
            throw AFPlayerError(ErrorReason.VIDEO_ID_ERROR, "Id Is Null Or Blank")
        return baseVideoURL.replace("{}", id)
    }

    fun getTitle() : String
    { return playPage.Get().title }
    fun getUrl() : String
    { return playPage.Get().url }
    fun getRecommandList() : MutableList<Bangumi>
    { return recommandList.Get() }

    var isFetched = MutableStateFlow(false)

    private val playPage = BangumiPlayPage()
    private val playList = BangumiPlayListList()
    private val recommandList = BangumiList()
}