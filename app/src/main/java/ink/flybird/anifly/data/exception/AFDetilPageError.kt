package ink.flybird.anifly.data.exception


enum class DetilPageReason {
    PAGE_ID_IS_NULL_OR_EMPTY,
    VIDEO_PLAYER_ERROR_DEFAULT
}

class AFDetilPageError (
    val reason : DetilPageReason,
    val additionalInfo : String = ""
) : Exception()