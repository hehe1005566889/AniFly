#include "globals.h"

#include "api/AnimationFly.hpp"

using namespace anicore;

void hello_from_lib()
{
    Logger::init();

    auto fly = AnimationFly::CreateFly(0);
    std::list<Bangumi> r;
    fly->Search("总之", 1, r);

    CInfo("Done.");

    std::list<BangumiPlayList> b;
    fly->DailyBangumi(5, b);

    CInfo("Done.");

    BangumiDetilPage page;
    fly->GetPlayList("http://www.yinghuacd.com/show/5856.html", page);

    CInfo("Done.");

    BangumiPlayPage pp;
    fly->GetPlayPage("http://www.yinghuacd.com/v/283-5.html", pp);

    CInfo("Done.");
    
    std::list<Bangumi> bt;
    std::string loc("74");
    fly->GetTheme(loc, 2, bt);

    CInfo("Done.");
}
