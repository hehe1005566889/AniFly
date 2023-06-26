package ink.flybird.anifly.ui.components

import android.util.Log
import android.view.contentcapture.ContentCaptureContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.util.MimeTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import ink.flybird.anifly.data.module.IODispatcher
import ink.flybird.anifly.ui.extension.collectAsStateValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class AFPlayerViewModel @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel()
{
    private val _playerState = MutableStateFlow(AFPlayerState())
    val playerState: StateFlow<AFPlayerState> = _playerState.asStateFlow()

    fun set(player: ExoPlayer)
    {
        _playerState.update {
            it.copy(
                player = MutableStateFlow(player),
                isPlayerAvailable = MutableStateFlow(true)
            )
        }
    }

    fun addMedia(uri : String, type : String)
    {
        val item = MediaItem.Builder().apply {
            setUri(uri)
            setMediaMetadata(MediaMetadata.Builder().setTitle("Widevine DASH cbcs: Tears").build())
            setMimeType(type)
            setDrmConfiguration(
                MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                    .build()
            )
        }.build()

        _playerState.update {
            it.copy(
                mediaItems = MutableStateFlow(item),
                isMediaAvailable = MutableStateFlow(true)
            )
        }
    }
}

@Deprecated("Use ExoPlayer Default To Instead")
enum class AFMediaType {
    M3U8, MP4
}

data class AFPlayerState(
    val player : Flow<ExoPlayer> = emptyFlow(),
    val mediaItems : Flow<MediaItem> = emptyFlow(),

    val isPlayerAvailable : Flow<Boolean> = MutableStateFlow(false),
    val isMediaAvailable : Flow<Boolean> = MutableStateFlow(false)
)
