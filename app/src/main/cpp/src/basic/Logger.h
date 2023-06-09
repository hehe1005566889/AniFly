#pragma once

#include <iostream>
#include <string.h>

#include <spdlog/spdlog.h>

#include <spdlog/sinks/android_sink.h>

#include <signal.h>

namespace anicore
{

    static std::string LastErrorMessage;

class Logger
{
public:
    static void init();

    inline static spdlog::logger* get_app_logger()
    { return alog.get(); }
    inline static spdlog::logger* get_core_logger()
    { return clog.get(); }

    static void set_format(spdlog::logger* logger, const char* format);
    static void push_error(int code, const char* msg);

private:
    static std::shared_ptr<spdlog::logger> alog, clog;
};

#define APP_LOGGER  ::anicore::Logger::get_app_logger()
#define CORE_LOGGER ::anicore::Logger::get_core_logger()

#define SET_LEVEL(LOGGER, LEVEL) LOGGER->set_level((spdlog::level::level_enum)LEVEL);
#define SET_PATTERN(LOGGER, PATTERN) ::anicore::Logger::set_format(LOGGER, PATTERN)

#define Info(...)    APP_LOGGER->info(__VA_ARGS__)
#define Warn(...)    APP_LOGGER->warn(__VA_ARGS__)
#define Error(MESSAGE,...)   APP_LOGGER->error(__VA_ARGS__); ::anicore::Logger::push_error(0,MESSAGE)
#define Debug(...)   APP_LOGGER->debug(__VA_ARGS__)

#define CInfo(...)   CORE_LOGGER->info(__VA_ARGS__)
#define CWarn(...)   CORE_LOGGER->warn(__VA_ARGS__)
#define CError(MESSAGE,...)  CORE_LOGGER->error(__VA_ARGS__); ::anicore::Logger::push_error(0,MESSAGE)
#define CDebug(...)  CORE_LOGGER->debug(__VA_ARGS__)

}
