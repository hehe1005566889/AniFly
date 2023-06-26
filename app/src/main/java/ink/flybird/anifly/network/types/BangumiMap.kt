package ink.flybird.anifly.network.types

import android.util.ArrayMap
import android.util.Log


data class BangumiItem(
    val title  : String,
    val image  : String,
    val url    : String,
    val time   : String
) {}

class BangumiMap
{
    private val bangumis : MutableMap<String, MutableList<BangumiItem>> = ArrayMap();

    fun add(stack : String, title : String, image : String, url: String, time: String)
    {
        if(!bangumis.contains(stack))
            bangumis[stack] = ArrayList()
        bangumis[stack]?.add(BangumiItem(time = time, title = title, image = image, url = url))
        Log.d(javaClass.name, "On Add stack : $stack, title : $title")
    }

    fun get() : MutableMap<String, MutableList<BangumiItem>>
    { return bangumis }
}