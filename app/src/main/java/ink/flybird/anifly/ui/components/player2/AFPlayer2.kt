package ink.flybird.anifly.ui.components.player2

import android.content.pm.ActivityInfo
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import ink.flybird.anifly.ui.components.AFDialog
import ink.flybird.anifly.ui.components.player.extensions.findActivity
import ink.flybird.anifly.ui.components.player.extensions.lockScreenDrientation
import ink.flybird.anifly.ui.components.player.extensions.setFullScreen

@Composable
fun rememberPlayer() : ExoPlayer {

    val context = LocalContext.current

    return remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                prepare()
            }
    }
}

@Composable
fun AFPlayer2(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    /*val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
               // val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))

              //  setMediaSource(source)
                prepare()
            }
    }*/

    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    DisposableEffect(
        AndroidView(modifier = modifier, factory = {
            PlayerView(context).apply {
                //hideController()
                useController = true
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        })
    ) {
        onDispose { exoPlayer.release() }
    }
}

@Composable
fun AFPlayer2FullScreenTest(
    exoPlayer: ExoPlayer
) {
    val context = LocalContext.current

    var state by remember { mutableStateOf(true) }

    if(state) {
        Dialog(
            onDismissRequest = {
                state = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                securePolicy = SecureFlagPolicy.SecureOff
            )
        ) {
            AFPlayer2(exoPlayer, Modifier.fillMaxSize())
            DisposableEffect(Unit) {

                val currentActivity = context.findActivity()
                //val mac = currentActivity as MainActivity
                //mac.changeState()

                //currentActivity.lockScreenDrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                currentActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                //currentActivity.setFullScreen(true)

                //(view.parent as DialogWindowProvider).window.setFullScreen(true)

                onDispose {
                    //currentActivity.requestedOrientation = (ActivityInfo.S)

                //    currentActivity.setFullScreen(false)
                    //  (view.parent as DialogWindowProvider).window.setFullScreen(false)
                }
            }

        }
    }
}