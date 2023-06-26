package ink.flybird.anifly

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import coil.ImageLoader
import dagger.hilt.android.HiltAndroidApp
import ink.flybird.anifly.data.module.ApplicationScope
import ink.flybird.anifly.data.module.IODispatcher
import ink.flybird.anifly.ui.components.player.media.AFPlayerCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class AniApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workManager: WorkManager

    //@Inject
    //lateinit var notificationHelper: NotificationHelper

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    @Inject
    @IODispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        CrashHandler(this)
        applicationScope.launch {
        }
    }

    /**
     * Override the [Configuration.Builder] to provide the [HiltWorkerFactory].
     */
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}