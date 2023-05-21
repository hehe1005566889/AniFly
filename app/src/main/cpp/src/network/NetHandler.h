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

        std::future<void> AsyncGetRequest(NetJob& job);
        void SyncGetRequest(NetJob& job);

        std::future<void> AsyncPostRequest(NetJob& job);
        void SyncPostRequest(NetJob& job);

    public:
        static ref<NetHandler> CreateHandler();
    };
}