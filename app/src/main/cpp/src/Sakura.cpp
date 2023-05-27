#include "Sakura.hpp"

#include <html_parser.hpp>

namespace anicore
{
#define BASIC_URL "http://www.yinghuacd.com/search/{}/?page={}"
#define BASE_URL "http://www.yinghuacd.com/"

    Sakura::Sakura()
    {
        _handler = NetHandler::CreateHandler();
        if(_handler.get() == nullptr)
        CError("EPTY");
    }

    void Sakura::Search(const std::string& keyWord, int page, std::list<Bangumi> &rs)
    {
        std::string kw(keyWord);
        EncodeUrl(kw);

        auto url = CreateUri(BASIC_URL, { kw.c_str(), std::to_string(page).c_str() });
        CInfo(url);

        NetJob job(JobType::AsyncGet, url, [&rs](JobResult code, NetJob &job)
        {
            std::string result;
            job.ToString(result);
            
            HtmlParser parser;
            shared_ptr<HtmlDocument> doc = parser.Parse(result.c_str(), result.size());
            
            auto el = doc->GetElementByClassName("lpic").at(0)->GetElementByTagName("li");

            std::list<Bangumi> bangumiList;

            for (const auto& item : el)
            {
                auto url = item->GetElementByTagName("a").at(0)->GetAttribute("href");

                auto img = item->GetElementByTagName("img").at(0);
                auto cov = img->GetAttribute("src");
                auto tit = img->GetAttribute("alt");
                
                auto dat = item->GetElementByTagName("span").at(0)->GetValue();
                auto dtp = item->GetElementByTagName("p");
                auto dtl = dtp.at(dtp.size() - 1)->GetValue();
                
                Bangumi bangumi { std::string(tit), std::string(cov), std::string(dtl), std::string(url), std::string(dat) };
                bangumiList.push_back(bangumi);
            }
            
            rs = bangumiList;
        });
        job.Action(_handler.get());
    }

    void Sakura::GetTheme(std::string& theme, int page, std::list<Bangumi> &rs)
    {
        EncodeUrl(theme);
        auto uri = BASE_URL + theme;
        if(page > 1)
        uri.append("/").append(std::to_string(page)).append(".html");
        else
        uri.append("/");

        CInfo(uri);

        NetJob job(JobType::AsyncGet, uri, [&rs](JobResult code, NetJob &job)
        {
            std::string result;
            job.ToString(result);

            HtmlParser parser;
            shared_ptr<HtmlDocument> doc = parser.Parse(result.c_str(), result.size());
            
            auto el = doc->GetElementByClassName("lpic").at(0)->GetElementByTagName("li");
            auto it = el.size();

            for(const auto& item : el)
            {
                Bangumi bangumi {};

                bangumi.url = item->GetElementByTagName("a").at(0)->GetAttribute("href");

                auto img = item->GetElementByTagName("img").at(0);
                bangumi.image = img->GetAttribute("src");
                bangumi.title = img->GetAttribute("alt");

                bangumi.time = item->GetElementByTagName("span").at(0)->text();
                
                auto p = item->GetElementByTagName("p");
                bangumi.detil = p.at(p.size() - 1)->text();

                rs.push_back(bangumi);
            }
        });
        job.Action(_handler.get()); 
    }

    /// @brief Get Current Day's Bangumi Update List
    /// @param day 0 for Monday
    /// @param rs Result Of Action
    void Sakura::DailyBangumi(int day, std::list<BangumiPlayList> &rs)
    {
        CInfo(BASE_URL);

        NetJob job(JobType::AsyncGet, BASE_URL, [&rs, &day](JobResult code, NetJob &job)
        {
            std::string result;
            job.ToString(result);
            
            HtmlParser parser;
            shared_ptr<HtmlDocument> doc = parser.Parse(result.c_str(), result.size());
            
            auto allDay = doc->GetElementByClassName("tlist").at(0)->GetElementByTagName("ul");
            auto toDay = allDay.at(day)->GetElementByTagName("li");

            for(const auto& item : toDay)
            {
                auto tp = item->GetElementByTagName("a");

                auto url = tp.at(tp.size() - 1)->GetAttribute("href");
                auto tit = tp.at(tp.size() - 1)->GetAttribute("title");
                
                BangumiPlayList bpl { std::string(tit), std::string(url) };
                rs.push_back(bpl);
            }
            
        });
        job.Action(_handler.get());
    }

    void Sakura::GetPlayList(const std::string& url, BangumiDetilPage &rs)
    {
        CInfo(url);

        NetJob job(JobType::AsyncGet, url, [&rs](JobResult code, NetJob &job)
        {
            std::string result;
            job.ToString(result);
            
            HtmlParser parser;
            shared_ptr<HtmlDocument> doc = parser.Parse(result.c_str(), result.size());

            auto img = doc->GetElementByTagName("img").at(0);

            // Get Bangumi Infos
            auto rr = doc->GetElementByClassName("rate r").at(0);
            rs.title = img->GetAttribute("alt");
            rs.image = img->GetAttribute("src");

            rs.detail = doc->GetElementByClassName("info").at(0)->text();

            rs.update = doc->GetElementByTagName("p").at(0)->text();
            
            auto sinfo = rr->GetElementByClassName("sinfo").at(0)->GetElementByTagName("span");
            rs.location = sinfo.at(1)->GetElementByTagName("a").at(0)->GetValue();

            auto types = sinfo.at(2)->GetElementByTagName("a");
            for(const auto& tp : types)
            {
            rs.type.append(" ").append(tp->GetValue());
            }

            rs.time = sinfo.at(0)->text();
            // ======================

            // Get Play List And Uris
            auto movurl = doc->GetElementByClassName("movurl").at(0)->GetElementByTagName("li");
            for(const auto& urlitm : movurl)
            {
                auto dv = urlitm->GetElementByTagName("a").at(0);
                BangumiPlayList ls { dv->GetValue(), dv->GetAttribute("href") };
                rs.result.push_back(ls);
            }
            // ======================

            // Get Recommand List
            auto pics = doc->GetElementByClassName("pics").at(0)->GetElementByTagName("li");
            for(const auto& picitm : pics)
            {
                Bangumi bangumi {};

                auto img = picitm->GetElementByTagName("img").at(0);
                bangumi.image = img->GetAttribute("src");
                bangumi.title = img->GetAttribute("alt");

                bangumi.time = picitm->GetElementByTagName("font").at(0)->GetValue();
                bangumi.detil = picitm->GetElementByTagName("p").at(0)->GetValue();
                bangumi.url = picitm->GetElementByTagName("a").at(0)->GetAttribute("href");

                rs.recommand.push_back(bangumi);
            }
            // =====================
        });
        job.Action(_handler.get());
    }

    void Sakura::GetPlayPage(const std::string& url, BangumiPlayPage &page)
    {
        CInfo(url);

        NetJob job(JobType::SyncGet, url, [&page](JobResult code, NetJob &job)
        {
            std::string result;
            job.ToString(result);

            HtmlParser parser;
            shared_ptr<HtmlDocument> doc = parser.Parse(result.c_str(), result.size());
            
            // Get Video Id
            page.videoUrl = doc->GetElementById("playbox")->GetAttribute("data-vid");
            // ============

            // Get Infomation Of Current Page
            auto tbar = doc->GetElementByClassName("gohome").at(0)->GetElementByTagName("h1").at(0);
            page.title.append(tbar->GetElementByTagName("a").at(0)->GetValue()).append(" ");
            page.title.append(tbar->GetElementByTagName("span").at(0)->GetValue());
            // ==============================


            // Get Play List And Uris
            auto movurl = doc->GetElementByClassName("movurls").at(0)->GetElementByTagName("li");
            for(const auto& urlitm : movurl)
            {
                auto dv = urlitm->GetElementByTagName("a").at(0);
                BangumiPlayList ls { dv->GetValue(), dv->GetAttribute("href") };
                page.result.push_back(ls);
            }
            // ======================

            // Get Recommand List
            auto pics = doc->GetElementByClassName("imgs").at(0)->GetElementByTagName("li");
            for(const auto& picitm : pics)
            {
                Bangumi bangumi {};

                auto img = picitm->GetElementByTagName("img").at(0);
                bangumi.image = img->GetAttribute("src");
                bangumi.title = img->GetAttribute("alt");

                auto time = picitm->GetElementByTagName("p");
                bangumi.time = time.at(time.size() - 1)->text();


                bangumi.url = picitm->GetElementByTagName("a").at(0)->GetAttribute("href");

                page.recommand.push_back(bangumi);
            }
            // =====================
        });
        job.Action(_handler.get());
    }
}