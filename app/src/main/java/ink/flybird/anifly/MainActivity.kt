package ink.flybird.anifly

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.profileinstaller.ProfileInstallerInitializer
import coil.ImageLoader
import coil.compose.LocalImageLoader
import dagger.hilt.android.AndroidEntryPoint
import ink.flybird.anifly.data.common.ShareInfo
import ink.flybird.anifly.data.common.ShareType
import ink.flybird.anifly.network.AniCore
import ink.flybird.anifly.network.AniCoreLoggerUtil
import ink.flybird.anifly.network.types.BangumiMap
import ink.flybird.anifly.ui.components.player.media.AFPlayerCache
import ink.flybird.anifly.ui.extension.showToast
import ink.flybird.anifly.ui.pages.MainEntry
import javax.inject.Inject


/**
 * The Single-Activity Architecture.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private var isInit = false
    private var context: Context? = null

    fun onCallBack(code : Int, vararg args : Any) {
        context?.showToast("App Error : \n ${args[0] as String}")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //land
        } else if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //port
        }
    }

    fun changeState()
    {
        /* 声明Display对象，以取得屏幕宽高 */
        val defaultDisplay: Display? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) display else window.windowManager.defaultDisplay
        val intScreenW = defaultDisplay?.width ?: 0
        val intScreenH = defaultDisplay?.width ?: 0
        /* 如果为Landscape */
        requestedOrientation = if(intScreenW > intScreenH) {
            /* Landscape => Portrait */
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else {
            /* Portrait => Landscape */
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!isInit)
        {
            AniCore.setLoggerLevel(AniCoreLoggerUtil.Debug)
            AniCore.initAPI(0, this)
            Log.d(javaClass.name, "On API Init For 0 And Debug Logger")
            AFPlayerCache.initialize(this, 1024 * 1024 * 1024)

            val map = BangumiMap()
            AniCore.getRecentUpdate(map);

            Log.d("awa", "qwq")
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        Log.i(javaClass.name, "onCreate: ${ProfileInstallerInitializer().create(this)}")

        val intent = intent

        setContent {

            context = LocalContext.current

            CompositionLocalProvider(
                LocalImageLoader provides imageLoader,
            ) {
                MainEntry(intent)
            }
        }

    }
}
