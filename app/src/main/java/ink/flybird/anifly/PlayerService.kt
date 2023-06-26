package ink.flybird.anifly

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PlayerService : Service()
{
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
}