package ink.flybird.anifly

import android.content.Context
import android.content.Intent
import android.os.Looper
import android.util.Log
import ink.flybird.anifly.ui.extension.showToastLong
import java.lang.Thread.UncaughtExceptionHandler

/**
 * The uncaught exception handler for the application.
 */
class CrashHandler(private val context: Context) : UncaughtExceptionHandler {

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * Catch all uncaught exception and log it.
     */
    override fun uncaughtException(p0: Thread, p1: Throwable) {
        val causeMessage = getCauseMessage(p1)

        p1.printStackTrace()
        Log.e(javaClass.name, "|> AnimationFly System Exception")
        Log.e(javaClass.name, "|> Message : $causeMessage")
        for(trace in p1.stackTrace)
            Log.e(javaClass.name, "|- at ${trace.lineNumber} from ${trace.className}:${trace.methodName} (in ${trace.fileName}, is Native Func : ${trace.isNativeMethod}")

        //context.startActivity(sendIntent)
        //context.showToastLong("AnimationFly System Error : $causeMessage")
    }

    private fun getCauseMessage(e: Throwable?): String? {
        val cause = getCauseRecursively(e)
        return cause?.message ?: e?.javaClass?.name
    }

    private fun getCauseRecursively(e: Throwable?): Throwable? {
        var cause: Throwable?
        cause = e
        while (cause?.cause != null && cause !is RuntimeException) {
            cause = cause.cause
        }
        return cause
    }
}
