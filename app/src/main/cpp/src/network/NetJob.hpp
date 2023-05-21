#pragma once 

#include "globals.h"

#include <fstream>
#include <vector>

namespace anicore
{

class NetHandler;

#define JobCallback void(*func)(JobResult code, NetJob& job)

    enum JobType
    { 
        AsyncGet , SyncGet,
        AsyncPost, SyncPost,

        Get, Post
    };

    enum JobResult
    { 
        Success = 1, Error = 2
    };

    class NetJob
    {
    public:
        NetJob(JobType type, std::string url, std::function<void(JobResult, NetJob&)> func);
        ~NetJob();

        void Release();

    private:
        bool HasJobType(JobType combination, JobType target)
        { return (combination & target) == target; }

        void HandleType(JobType type);

    public: 
        void Action(NetHandler* handler);
        void OnFininshed();
        void OnError();

        void ToString(std::string& str);
        void ToFile(const char* path);
        void ToStream(std::ofstream& stream);

    public:
        std::string url;
        std::vector<std::uint8_t> _response;

        std::string post_args;
        
        std::function<void(JobResult, NetJob&)> back;
        JobType current_type;
        bool use_async;
    };
}