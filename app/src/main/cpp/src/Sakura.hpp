#pragma once

#include "api/AnimationFly.hpp"

#include "globals.h"
#include "network/NetUtil.hpp"
#include "network/NetHandler.h"
#include "network/NetJob.hpp"


namespace anicore
{
    class Sakura : public AnimationFly
    {
    public:
        Sakura();

    public:
        virtual void Search(const std::string& keyWord, int page, std::list<Bangumi> &reusult) override;
        virtual void GetTheme(std::string& theme, int page, std::list<Bangumi> &reusult) override;
        virtual void DailyBangumi(int day, std::list<BangumiPlayList> &reusult) override; 
        
        virtual void GetPlayList(const std::string& url, BangumiDetilPage &result) override;
        virtual void GetPlayPage(const std::string& url, BangumiPlayPage &page) override;
        virtual void GetRecentUpdate(std::map<std::string, std::list<Bangumi>> &result) override;
        
    private:
        ref<NetHandler> _handler;
    };
}