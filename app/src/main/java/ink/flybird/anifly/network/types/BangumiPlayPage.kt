package ink.flybird.anifly.network.types

data class PageInfo(
    val title : String,
    val url : String
)

class BangumiPlayPage {

    private lateinit var info : PageInfo

    fun Set(title: String, url: String)
    { info = PageInfo(title = title, url = url.replace("$", "[]").replace("[]mp4", "")) }

    fun Get() : PageInfo
    { return info }
}