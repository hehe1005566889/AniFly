package ink.flybird.anifly.network.types

data class BangumiDetail(
    val title : String,
    val location: String,
    val time : String,
    val type : String,
    val img : String,
    val update : String,
    val detail : String
)

class BangumiDetailPage
{
    private lateinit var detail : BangumiDetail

    fun Set(title : String, location: String, time: String, type: String, img: String, update: String, d: String)
    { detail = BangumiDetail(title = title, time = time, location =  location, type = type, img = img, update = update, detail = d) }

    fun Get() : BangumiDetail
    { return detail }
}