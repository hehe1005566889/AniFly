package ink.flybird.anifly.ui.pages.home.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ink.flybird.anifly.data.exception.AFCommonError
import ink.flybird.anifly.data.exception.CommonError
import ink.flybird.anifly.data.module.IODispatcher
import ink.flybird.anifly.network.AniCore
import ink.flybird.anifly.network.types.Bangumi
import ink.flybird.anifly.network.types.BangumiList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) :  ViewModel() {


    private val _searchUiState = MutableStateFlow(SearchViewModelData())
    val searchUiState: StateFlow<SearchViewModelData> = _searchUiState.asStateFlow()
    private var _syncState = false

    fun setEnd() {
        _searchUiState.update {
            it.copy(
                isEnd = MutableStateFlow(true)
            )
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun nextPage(id : String, page : Int) {
        GlobalScope.launch {

            if(!_syncState)
                return@launch

            val url = createUri(id)
            val list = BangumiList()
            val result = AniCore.searchBangumi(url, page, list)

            if(!result)
            {
                Log.e(javaClass.name, "On Fetch Search Data Faild")
            }

            _searchUiState.update {
                it.copy(
                    title = MutableStateFlow(id),
                    results = MutableStateFlow(list.Get()),
                    isEnd = MutableStateFlow(false)
                )
            }
        }
    }

    fun syncData(id: String) {
        viewModelScope.launch(ioDispatcher)
        {
            if(_syncState)
                return@launch
            _syncState = true

            val url = createUri(id)
            val list = BangumiList()
            val result = AniCore.searchBangumi(url, 0, list)
            //if(!result)
            //{
            //    Log.e(javaClass.name, "On Fetch Search Data Faild")
            //}

            _searchUiState.update {
                it.copy(
                    title = MutableStateFlow(id),
                    results = MutableStateFlow(list.Get()),
                    isEnd = MutableStateFlow(false),
                    queueState = MutableStateFlow(true)
                )
            }

        }
    }

    fun dispose() {
        _searchUiState.update {
            it.copy(
                title = emptyFlow(),
                results = emptyFlow(),
                isEnd = emptyFlow(),
                page = emptyFlow()
            )
        }
    }

    private fun createUri(
        keyWord : String
    ) : String {
        if(keyWord.isBlank())
            throw AFCommonError(CommonError.SearchError)
        return keyWord
    }
}


data class SearchViewModelData (
    val title : Flow<String> = emptyFlow(),
    val results : Flow<MutableList<Bangumi>> = emptyFlow(),
    val page : Flow<Int> = MutableStateFlow(0),
    val isEnd : Flow<Boolean> = MutableStateFlow(false),

    val queueState : Flow<Boolean> = emptyFlow()
)