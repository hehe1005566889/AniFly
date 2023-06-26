#include "NetHandler.h"

#include "NetJob.hpp"

namespace anicore
{
    NetHandler::NetHandler()
    = default;

    std::future<void> NetHandler::AsyncGetRequest(NetJob &job)
    {
        return std::async(
            std::launch::async,
            [ &job]()
            {
#ifdef Normal
                try {
#endif
                    http::Request request{job.url};
                    const auto response = request.send("GET", "",
                                                       {{"Content-Type", "application/x-www-form-urlencoded"},
                                                        {"User-Agent",   "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Safari/605.1.15"},
                                                        {"Accept",       "*/*"}},
                                                       std::chrono::seconds(2));

                    if (response.status.code == http::Status::Ok) {
                        job._response = response.body;
                        job.OnFininshed();
                    } else {
                        job.OnError();
                    }

#ifdef Normal
                } catch (...) {
                    CError("NetWork Request Error Or Handle Response Error", "[Net] Request Error");
                    job.OnError();
                }
#endif
            });
    }

    void NetHandler::SyncGetRequest(NetJob &job)
    {
#ifdef Normal
        try {
#endif
            http::Request request{job.url};
            const auto response = request.send("GET", "",
                                               {{"Content-Type", "application/x-www-form-urlencoded"},
                                                {"User-Agent",   "runscope/0.1"},
                                                {"Accept",       "*/*"}}, std::chrono::seconds(2));
            job._response = response.body;

            if (response.status.code == http::Status::Ok) {
                job.OnFininshed();
            } else {
                job.OnError();
            }
#ifdef Normal
        } catch (...) {
            CError("NetWork Request Error Or Handle Response Error", "[Net] Request Error");
            job.OnError();
        }
#endif
    }

    std::future<void> NetHandler::AsyncPostRequest(NetJob &job)
    {
        return std::async(
            std::launch::async,
            [ &job]()
            {

#ifdef Normal
                try
                {
#endif
                http::Request request{job.url};
                const auto response = request.send("POST", job.post_args, {{"Content-Type",
                                                                            "application/x-www-form-urlencoded"}});
                job._response = response.body;
                job.OnFininshed(); // std::cout << std::string{response.body.begin(), response.body.end()} << '\n'; // print the result


#ifdef Normal
                }
                                catch (...)
                                {
                                    CError("NetWork Request Error Or Handle Response Error", "[Net] Request Error [Mode:POST]");
                                    job.OnError();
                                }
#endif
            });
    }

    void NetHandler::SyncPostRequest(NetJob &job)
    {

#ifdef Normal
        try
        {
#endif
            http::Request request{job.url};
            const auto response = request.send("POST", job.post_args, {{"Content-Type", "application/x-www-form-urlencoded"}});
            job._response = response.body;
            job.OnFininshed(); // std::cout << std::string{response.body.begin(), response.body.end()} << '\n'; // print the result

#ifdef Normal
        }

        catch (...)
        {
            CError("NetWork Request Error Or Handle Response Error", "[Net] Request Error [Mode:POST]");
            job.OnError();
        }
#endif
    }

    ref<NetHandler> NetHandler::CreateHandler()
    {
        ref<NetHandler> handler;
        handler.reset(new NetHandler());
        return handler;
    }
}