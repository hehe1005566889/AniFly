package ink.flybird.anifly.ui.components.player


import androidx.compose.runtime.Stable
import com.google.android.exoplayer2.Player
import java.lang.IllegalStateException

/**
 * VideoPlayer repeat mode.
 */
@Stable
@Suppress("UNUSED_PARAMETER")
enum class AFRepeatMode(rawValue: String) {
    /**
     * No repeat.
     */
    NONE("none"),

    /**
     * Repeat current media only.
     */
    ONE("one"),

    /**
     * Repeat all track.
     */
    ALL("all"),
}

/**
 * Convert [RepeatMode] to exoplayer repeat mode.
 *
 * @return [Player.REPEAT_MODE_ALL] or [Player.REPEAT_MODE_OFF] or [Player.REPEAT_MODE_ONE] or
 */
internal fun AFRepeatMode.toExoPlayerRepeatMode(): Int =
    when (this) {
        AFRepeatMode.NONE -> Player.REPEAT_MODE_OFF
        AFRepeatMode.ALL -> Player.REPEAT_MODE_ALL
        AFRepeatMode.ONE -> Player.REPEAT_MODE_ONE
    }

/**
 * Convert exoplayer repeat mode to [RepeatMode].
 *
 * @return [RepeatMode.ALL] or [RepeatMode.NONE] or [RepeatMode.ONE]
 */
fun Int.toRepeatMode(): AFRepeatMode =
    if (this in 0 until 3) {
        when (this) {
            0 -> AFRepeatMode.NONE
            1 -> AFRepeatMode.ONE
            2 -> AFRepeatMode.ALL
            else -> throw IllegalStateException("This is not ExoPlayer repeat mode.")
        }
    } else {
        throw IllegalStateException("This is not ExoPlayer repeat mode.")
    }
