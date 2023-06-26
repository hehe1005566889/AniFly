package ink.flybird.anifly.network.types

data class Bangumi(
    val title  : String,
    val image  : String,
    val detail : String,
    val url    : String,
    val time   : String
) {}

class BangumiList
{
    private val bangumis : MutableList<Bangumi> = ArrayList();

    fun Add(title : String, image : String, detail: String, url: String, time: String)
    { bangumis.add(Bangumi(time = time, title = title, image = image, detail = detail, url = url)) }

    fun Get() : MutableList<Bangumi>
    { return bangumis }
}