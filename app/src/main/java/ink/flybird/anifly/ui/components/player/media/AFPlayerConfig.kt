package ink.flybird.anifly.ui.components.player.media


import android.view.View
import androidx.compose.runtime.Immutable
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ui.StyledPlayerView

/**
 * Sets the detailed properties of the [io.sanghun.compose.video.VideoPlayer] Controller.
 *
 * @param showSpeedAndPitchOverlay Visible speed, audio track select button.
 * @param showSubtitleButton Visible subtitle (CC) button.
 * @param showCurrentTimeAndTotalTime Visible currentTime, totalTime text.
 * @param showBufferingProgress Visible buffering progress.
 * @param showForwardIncrementButton Show forward increment button.
 * @param showBackwardIncrementButton Show backward increment button.
 * @param showBackTrackButton Show back track button.
 * @param showNextTrackButton Show next track button.
 * @param showRepeatModeButton Show repeat mode toggle button.
 * @param controllerShowTimeMilliSeconds Sets the playback controls timeout.
 *  The playback controls are automatically hidden after this duration of time has elapsed without user input and with playback or buffering in progress.
 *  (The timeout in milliseconds. A non-positive value will cause the controller to remain visible indefinitely.)
 * @param controllerAutoShow Sets whether the playback controls are automatically shown when playback starts, pauses, ends, or fails.
 *  If set to false, the playback controls can be manually operated with {@link #showController()} and {@link #hideController()}.
 *  (Whether the playback controls are allowed to show automatically.)
 * @param showFullScreenButton Show full screen button.
 */
@Immutable
data class AFPlayerConfig (
    val showSpeedAndPitchOverlay: Boolean,
    val showSubtitleButton: Boolean,
    val showCurrentTimeAndTotalTime: Boolean,
    val showBufferingProgress: Boolean,
    val showForwardIncrementButton: Boolean,
    val showBackwardIncrementButton: Boolean,
    val showBackTrackButton: Boolean,
    val showNextTrackButton: Boolean,
    val showRepeatModeButton: Boolean,
    val showFullScreenButton: Boolean,

    val controllerShowTimeMilliSeconds: Int,
    val controllerAutoShow: Boolean,
) {

    companion object {
        /**
         * Default config for Controller.
         */
        val Default = AFPlayerConfig (
            showSpeedAndPitchOverlay = false,
            showSubtitleButton = true,
            showCurrentTimeAndTotalTime = true,
            showBufferingProgress = false,
            showForwardIncrementButton = false,
            showBackwardIncrementButton = false,
            showBackTrackButton = true,
            showNextTrackButton = true,
            showRepeatModeButton = false,
            controllerShowTimeMilliSeconds = 5_000,
            controllerAutoShow = true,
            showFullScreenButton = true,
        )
    }
}

/**
 * Apply the [VideoPlayerControllerConfig] to the ExoPlayer StyledViewPlayer.
 *
 * @param playerView [StyledPlayerView] to which you want to apply settings.
 * @param onFullScreenStatusChanged Callback that occurs when the full screen status changes.
 */
internal fun AFPlayerConfig.applyToExoPlayerView(
    playerView: StyledPlayerView,
    onFullScreenStatusChanged: (Boolean) -> Unit,
) {
    val controllerView = playerView.rootView

    controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_settings).isVisible =
        showSpeedAndPitchOverlay
    playerView.setShowSubtitleButton(showSubtitleButton)
    controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_time).isVisible =
        showCurrentTimeAndTotalTime
    playerView.setShowBuffering(
        if (!showBufferingProgress) StyledPlayerView.SHOW_BUFFERING_NEVER else StyledPlayerView.SHOW_BUFFERING_ALWAYS,
    )
    controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_ffwd_with_amount).isVisible =
        showForwardIncrementButton
    controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_rew_with_amount).isVisible =
        showBackwardIncrementButton
    playerView.setShowNextButton(showNextTrackButton)
    playerView.setShowPreviousButton(showBackTrackButton)
    playerView.setShowFastForwardButton(showForwardIncrementButton)
    playerView.setShowRewindButton(showBackwardIncrementButton)
    playerView.controllerShowTimeoutMs = controllerShowTimeMilliSeconds
    playerView.controllerAutoShow = controllerAutoShow

    @Suppress("DEPRECATION")
    if (showFullScreenButton) {
        playerView.setControllerOnFullScreenModeChangedListener {
            onFullScreenStatusChanged(it)
        }
    } else {
        playerView.setControllerOnFullScreenModeChangedListener(null)
    }
}
