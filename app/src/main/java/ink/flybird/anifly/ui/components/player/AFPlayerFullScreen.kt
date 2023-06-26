package ink.flybird.anifly.ui.components.player


import android.content.pm.ActivityInfo
import android.view.WindowManager
import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.RepeatModeUtil
import ink.flybird.anifly.ui.components.player.extensions.findActivity
import ink.flybird.anifly.ui.components.player.extensions.lockScreenDrientation
import ink.flybird.anifly.ui.components.player.extensions.setFullScreen
import ink.flybird.anifly.ui.components.player.media.AFPlayerConfig
import ink.flybird.anifly.ui.components.player.media.applyToExoPlayerView

var fullScreenState = false

/**
 * ExoPlayer does not support full screen views by default.
 * So create a full screen modal that wraps the Compose Dialog.
 *
 * Delegate all functions of the video controller that were used just before
 * the full screen to the video controller managed by that component.
 * Conversely, if the full screen dismissed, it will restore all the functions it delegated
 * for synchronization with the video controller on the full screen and the video controller on the previous screen.
 *
 * @param player Exoplayer instance.
 * @param currentPlayerView [StyledPlayerView] instance currently in use for playback.
 * @param fullScreenPlayerView Callback to return all features to existing video player controller.
 * @param controllerConfig Player controller config. You can customize the Video Player Controller UI.
 * @param repeatMode Sets the content repeat mode.
 * @param enablePip Enable PIP.
 * @param onDismissRequest Callback that occurs when modals are closed.
 * @param securePolicy Policy on setting [android.view.WindowManager.LayoutParams.FLAG_SECURE] on a full screen dialog window.
 */
@Composable
internal fun VideoPlayerFullScreenDialog(
    player: ExoPlayer,
    currentPlayerView: StyledPlayerView,
    fullScreenPlayerView: StyledPlayerView.() -> Unit,
    controllerConfig: AFPlayerConfig,
    repeatMode: AFRepeatMode,
    enablePip: Boolean,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    val fullScreenPlayerView = remember {
        StyledPlayerView(context)
            .also(fullScreenPlayerView)
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {

        val view = LocalView.current
        DisposableEffect(Unit) {

            StyledPlayerView.switchTargetView(player, currentPlayerView, fullScreenPlayerView)
            val currentActivity = context.findActivity()

            // Set Kepp Screen In Activity
            currentActivity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            currentActivity.lockScreenDrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            //currentActivity.setFullScreen(true)

            val window = (view.parent as DialogWindowProvider).window
            window.setFullScreen(true)

            onDispose {
                currentActivity.lockScreenDrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                currentActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                //currentActivity.setFullScreen(false)
                (view.parent as DialogWindowProvider).window.setFullScreen(false)
            }
        }

        LaunchedEffect(controllerConfig) {
            controllerConfig.applyToExoPlayerView(fullScreenPlayerView) {
                if (!it) {
                    onDismissRequest()
                }
            }
            fullScreenPlayerView.findViewById<ImageButton>(com.google.android.exoplayer2.ui.R.id.exo_fullscreen)
                .performClick()
        }

        LaunchedEffect(controllerConfig, repeatMode) {
            fullScreenPlayerView.setRepeatToggleModes(
                if (controllerConfig.showRepeatModeButton) {
                    RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL or RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE
                } else {
                    RepeatModeUtil.REPEAT_TOGGLE_MODE_NONE
                },
            )
        }

        VideoPlayerSurface(
            defaultPlayerView = fullScreenPlayerView,
            player = player,
            usePlayerController = true,
            handleLifecycle = !enablePip,
            enablePip = enablePip,
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) { onDismissRequest() }
        //.align(Alignment.Center),

    }
}
