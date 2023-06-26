package ink.flybird.anifly.ui.components.player.extensions

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

internal fun Activity.lockScreenDrientation(orientation: Int) {
    val activity = this
    activity.requestedOrientation = orientation
}

@Composable
fun LockScreenOrientation(activity: ComponentActivity, orientation: Int) {
    DisposableEffect(Unit) {
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}