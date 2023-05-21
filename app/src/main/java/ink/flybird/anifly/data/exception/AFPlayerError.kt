package ink.flybird.anifly.data.exception

enum class ErrorReason {
    VIDEO_URL_EMPTY,
    VIDEO_ID_ERROR,
    VIDEO_PLAYER_ERROR_DEFAULT
}

class AFPlayerError (
    val reason : ErrorReason,
    val additionalInfo : String = ""
) : Exception()