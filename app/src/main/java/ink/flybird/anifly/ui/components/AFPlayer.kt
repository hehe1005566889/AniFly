package ink.flybird.anifly.ui.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.util.MimeTypes
import ink.flybird.anifly.ui.components.player.AFPlayer
import ink.flybird.anifly.ui.components.player.AFRepeatMode
import ink.flybird.anifly.ui.components.player.media.AFMediaItem
import ink.flybird.anifly.ui.components.player.media.AFPlayerConfig

class AFPlayerController
{
    private lateinit var player : ExoPlayer
    private lateinit var item : MediaItem

    fun set(player: ExoPlayer)
    {
        this.player = player

    }
    fun get() : ExoPlayer
    { return player }

    fun setUp()
    {
        player.addMediaItem(item)
    }

    fun setUri(uri : String)
    {
        item = MediaItem.Builder().apply {
            setUri(uri)
            setMediaMetadata(MediaMetadata.Builder().setTitle("Widevine DASH cbcs: Tears").build())
            setMimeType(MimeTypes.APPLICATION_M3U8)
            setDrmConfiguration(
                MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                    .build()
            )
        }.build()
    }
}

@Composable
fun AFVideoPlayer(
    controller: AFPlayerController
) {

    AFPlayer(
        mediaItems = ArrayList(),
        handleLifecycle = true,
        autoPlay = false,
        usePlayerController = true,
        enablePip = true,
        handleAudioFocus = true,
        volume = 0.5f,  // volume 0.0f to 1.0f
        repeatMode = AFRepeatMode.NONE,       // or RepeatMode.ALL, RepeatMode.ONE
        onCurrentTimeChanged = { // long type, current player time (millisec)
            //Log.e("CurrentTime", it.toString())
        },
        controllerConfig = AFPlayerConfig(
            showSpeedAndPitchOverlay = true,
            showSubtitleButton = true,
            showCurrentTimeAndTotalTime= true,
            showBufferingProgress = true,
            showForwardIncrementButton = true,
            showBackwardIncrementButton = false,
            showBackTrackButton = false,
            showNextTrackButton = false,
            showRepeatModeButton = false,
            showFullScreenButton = true,

            controllerShowTimeMilliSeconds = 10000,
            controllerAutoShow = true,
        ),
        playerInstance = {

            val instance : ExoPlayer = this
            controller.set(instance)

        },
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(145.dp, 145.dp),
    )



}
