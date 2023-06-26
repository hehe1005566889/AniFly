#include "NetJob.hpp"

#include "NetHandler.h"

#include <filesystem>
#include <utility>

namespace anicore
{
    NetJob::NetJob(JobType type, std::string url, std::function<void(JobResult, NetJob&)> func)
        : url(std::move(url)), back(std::move(func))
    {
        HandleType(type);
    }

    NetJob::~NetJob()
    = default;

    void NetJob::Release()
    {
    }

    void NetJob::HandleType(JobType type)
    {
        switch (type)
        {
        case JobType::SyncGet:
            this->current_type = JobType::Get;
            break;
        case JobType::AsyncGet:
            this->current_type = JobType::Get;
            use_async = true;
            break;
        case JobType::SyncPost:
            this->current_type = JobType::Post;
            break;
        case JobType::AsyncPost:
            this->current_type = JobType::Post;
            use_async = true;
            break;
        default:
            throw new NetWorkException(0, "[None]", "Job Type Format Error");
            break;
        }
    }

    void NetJob::Action(NetHandler *handler)
    {
        if (this->current_type == JobType::Get)
        {
            if (use_async)
                handler->AsyncGetRequest(*this).get();
            else
                handler->SyncGetRequest(*this);
        }
        else
        {
            if (use_async)
                handler->AsyncPostRequest(*this).get();
            else
                handler->SyncPostRequest(*this);
        }
    }

    void NetJob::OnFininshed()
    {
        back(JobResult::Success, *this);
    }

    void NetJob::OnError()
    {
        back(JobResult::Error, *this);
    }

    void NetJob::ToString(std::string &str)
    {
        str = std::string{_response.begin(), _response.end()};
    }

    void NetJob::ToFile(const char *path)
    {
        std::ofstream outfile(path, std::ios::out | std::ios::binary);

        if (!outfile.is_open())
        CError("Open Write File Error", "=> [NetMan] Open File Faild");

        outfile.write(reinterpret_cast<const char *>(_response.data()),
                      static_cast<std::streamsize>(_response.size()));
        // outfile.flush();
        outfile.close();
    }

    void NetJob::ToStream(std::ofstream &stream)
    {
        stream.write(reinterpret_cast<const char *>(_response.data()),
                      static_cast<std::streamsize>(_response.size()));
        stream.flush();
    }
}