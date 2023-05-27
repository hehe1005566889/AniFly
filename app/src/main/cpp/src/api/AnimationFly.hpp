#pragma once

#include "globals.h"

#include <list>

namespace anicore
{
    /// @brief For Search
    struct Bangumi
    {
        std::string title;
        std::string image;
        std::string detil;
        std::string url;
        std::string time;
    };

    /// @brief For Play List
    struct BangumiPlayList
    {
        /// @brief Bangumi Title
        std::string title;
        /// @brief Detil Page's Uri
        std::string url;
    };

    struct BangumiDetilPage
    {
        //fire l   // sinfo

        std::string title;
        std::string time;
        std::string location;
        std::string type;
        std::string image;
        std::string update;
        std::string detail;
        
        std::list<BangumiPlayList> result;
        /// @brief Bangumi Recommands
        std::list<Bangumi> recommand;
    };

    /// @brief PlayPage's Detil
    struct BangumiPlayPage
    {
        /// @brief Page's Title
        std::string title;
        /// @brief Video Uri
        std::string videoUrl;

        std::list<BangumiPlayList> result;
        /// @brief Bangumi Recommands
        std::list<Bangumi> recommand;
    };

    class AnimationFly
    {
    public:
        /// @brief Search Bangumi
        /// @param keyWord KeyWord
        /// @param page Result Page
        /// @param reusult Need To Give A List To Save The Result
        virtual void Search(const std::string& keyWord, int page, std::list<Bangumi> &reusult) = 0;
        virtual void GetTheme(std::string& theme, int page, std::list<Bangumi> &reusult) = 0;
        virtual void DailyBangumi(int day, std::list<BangumiPlayList> &reusult) = 0;
        
        virtual void GetPlayList(const std::string& url, BangumiDetilPage& page) = 0;
        virtual void GetPlayPage(const std::string& url, BangumiPlayPage &result) = 0;

    public:
        static ref<AnimationFly> CreateFly(int api);
    };
}