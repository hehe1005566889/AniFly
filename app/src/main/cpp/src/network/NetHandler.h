#pragma once

#include "globals.h"

#include <HTTPRequest.hpp>
#include <future>

namespace anicore
{
    class NetJob;

    class NetHandler
    {
    private:
        NetHandler();

    public:
        ~NetHandler() {};

        static std::future<void> AsyncGetRequest(NetJob& job);
        static void SyncGetRequest(NetJob& job);

        static std::future<void> AsyncPostRequest(NetJob& job);
        static void SyncPostRequest(NetJob& job);

    public:
        static ref<NetHandler> CreateHandler();
    };
}