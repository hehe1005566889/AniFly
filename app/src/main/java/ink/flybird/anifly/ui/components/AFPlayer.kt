package ink.flybird.anifly.ui.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.ExoPlayer
import ink.flybird.anifly.data.exception.AFPlayerError
import ink.flybird.anifly.data.exception.ErrorReason
import ink.flybird.anifly.ui.components.player.AFPlayer
import ink.flybird.anifly.ui.components.player.AFRepeatMode
import ink.flybird.anifly.ui.components.player.media.AFPlayerConfig
import ink.flybird.anifly.ui.extension.collectAsStateValue

@Composable
fun AFVideoPlayer(
    controller: AFPlayerViewModel,
    onFullScreenEnter: () -> Unit = {},
    onFullScreenExit: () -> Unit = {}
) {

    val playerState = controller.playerState.collectAsStateValue()

    val player = playerState.player.collectAsStateValue(initial = null)
    val media = playerState.mediaItems.collectAsStateValue(initial = null)

    val mediaState = playerState.isMediaAvailable.collectAsStateValue(initial = false)
    val playerAvaState = playerState.isPlayerAvailable.collectAsStateValue(initial = false)

    LaunchedEffect(mediaState) {
        if (mediaState && playerAvaState) {
            if (player == null)
                throw AFPlayerError(ErrorReason.VIDEO_PLAYER_NOT_INIT)
            if (media == null)
                throw AFPlayerError(ErrorReason.VIDEO_PLAYER_MEDIA_NOT_INIT)
            player.addMediaItem(media)
        }
    }

    LaunchedEffect(playerState) {
        if (mediaState && playerAvaState) {
            if (player == null)
                throw AFPlayerError(ErrorReason.VIDEO_PLAYER_NOT_INIT)
            if (media == null)
                throw AFPlayerError(ErrorReason.VIDEO_PLAYER_MEDIA_NOT_INIT)
            player.addMediaItem(media)
        }
    }

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
            showCurrentTimeAndTotalTime = true,
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

            val instance: ExoPlayer = this
            controller.set(instance)

        },
        onFullScreenEnter = onFullScreenEnter,
        onFullScreenExit = onFullScreenExit,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(145.dp, 145.dp),
    )


}
