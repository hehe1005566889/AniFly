package ink.flybird.anifly.ui.pages.home.main.today

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import ink.flybird.anifly.data.module.IODispatcher
import ink.flybird.anifly.network.AniCore
import ink.flybird.anifly.network.types.BangumiPlayList
import ink.flybird.anifly.network.types.BangumiPlayListList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TodayViewModel  @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _todayUIState = MutableStateFlow(TodayUIState())
    val todayUIState: StateFlow<TodayUIState> = _todayUIState.asStateFlow()
    private var _syncState = false

    fun syncData(
        navController: NavController
    ) {

        viewModelScope.launch(ioDispatcher)
        {
            if (_syncState)
                return@launch
            _syncState = true

            val list = BangumiPlayListList()
            val result = AniCore.getDailyBangumi(getDate(), list)
            if(!result)
            {
                Log.e(javaClass.name, "ToDay View Fetch Data Faild")
                navController.navigateUp()
            }

            _todayUIState.update {
                it.copy(
                    items = MutableStateFlow(list.Get()),
                    done = MutableStateFlow(true)
                )
            }
        }
    }

    private fun getDate(): Int {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return if (dayOfWeek == Calendar.SUNDAY) 6 else dayOfWeek - 2
    }


}


data class TodayUIState(
    val items : Flow<List<BangumiPlayList>> = emptyFlow(),
    val done : Flow<Boolean> = emptyFlow()
)