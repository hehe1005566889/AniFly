package ink.flybird.anifly.ui.utils

import android.content.Context
import android.os.PowerManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

@Composable
fun SetKeepScreen()
{

    val context = LocalContext.current

    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    val wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, ":SensorMode");
    wakeLock?.acquire(10*60*1000L)
}

@Composable
fun ReSetKeepScreen()
{

    val context = LocalContext.current

    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    val wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, ":SensorMode");
    wakeLock?.setReferenceCounted(false)
    wakeLock?.release()
}

