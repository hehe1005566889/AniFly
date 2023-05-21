package ink.flybird.anifly

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.profileinstaller.ProfileInstallerInitializer
import coil.ImageLoader
import coil.compose.LocalImageLoader
import dagger.hilt.android.AndroidEntryPoint
import ink.flybird.anifly.network.AniCore
import ink.flybird.anifly.network.AniCoreLoggerUtil
import ink.flybird.anifly.ui.components.player.media.AFPlayerCache
import ink.flybird.anifly.ui.pages.MainEntry
import java.nio.file.Files
import javax.inject.Inject

/**
 * The Single-Activity Architecture.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private var isInit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!isInit)
        {
            AniCore.setLoggerLevel(AniCoreLoggerUtil.Debug)
            AniCore.initAPI(0)
            Log.d(javaClass.name, "On API Init For 0 And Debug Logger")
            AFPlayerCache.initialize(this, 1024 * 1024 * 1024)
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        Log.i(javaClass.name, "onCreate: ${ProfileInstallerInitializer().create(this)}")

        setContent {
            CompositionLocalProvider(
                LocalImageLoader provides imageLoader,
            ) {
                MainEntry()
            }
        }
    }
}
