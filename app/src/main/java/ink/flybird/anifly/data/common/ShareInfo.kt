package ink.flybird.anifly.data.common

enum class ShareType {
    Detil,
    Play
}

data class ShareInfo(
    val type: ShareType,
    val id : String?
)

fun createShareUri(
    id : String
) : String {
    return "https://fbtstudio.gitee.io/studioweb/anifly?${id}"
}
