package ink.flybird.anifly.network.types

data class BangumiDetail(
    val title : String,
    val location: String,
    val time : String,
    val type : String
)

class BangumiDetailPage
{
    private lateinit var detail : BangumiDetail

    fun Set(title : String, location: String, time: String, type: String)
    { detail = BangumiDetail(title = title, time = time, location =  location, type = type) }

    fun Get() : BangumiDetail
    { return detail }
}