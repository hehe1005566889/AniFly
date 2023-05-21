package ink.flybird.anifly.network.types

data class BangumiPlayList(
    val title : String,
    val url : String
)

class BangumiPlayListList
{
    private val list : MutableList<BangumiPlayList> = ArrayList()

    fun Add(title : String, url: String)
    { list.add(BangumiPlayList(title = title, url = url)) }

    fun Get() : MutableList<BangumiPlayList>
    { return list }
}