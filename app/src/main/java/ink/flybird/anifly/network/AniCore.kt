package ink.flybird.anifly.network

import ink.flybird.anifly.network.types.BangumiDetailPage
import ink.flybird.anifly.network.types.BangumiList
import ink.flybird.anifly.network.types.BangumiPlayList
import ink.flybird.anifly.network.types.BangumiPlayListList
import ink.flybird.anifly.network.types.BangumiPlayPage

object AniCoreLoggerUtil
{
    const val Trace = 0
    const val Debug = 1
    const val Info = 2
    const val Warn = 3
    const val Error = 4
    const val Critical = 5
    const val Off = 6
}

object AniCore {

    external fun setLoggerLevel(level : Int)
    external fun initAPI(api : Int);

    external fun searchBangumi(keyWord : String, page : Int, result: BangumiList)
    external fun getBangumiByTheme(theme : String, page : Int, result: BangumiList)
    external fun getDailyBangumi(day : Int, result: BangumiPlayListList)

    external fun getPlayPage(url : String, page : BangumiPlayPage, result: BangumiPlayListList, rs : BangumiList)
    external fun getPlayList(url : String, page : BangumiDetailPage, result: BangumiPlayListList, rs: BangumiList)

    init {
        System.loadLibrary("anifly")
    }
}