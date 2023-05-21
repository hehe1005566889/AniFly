#include "Logger.h"

namespace anicore
{

std::shared_ptr<spdlog::logger> Logger::alog;
std::shared_ptr<spdlog::logger> Logger::clog;

#define FORMAT "[%H:%M:%S %z] [%^%L%$] [thread %t] %n : %v"

#ifdef cb_debug
#define LEVEL spdlog::level::debug
#else
#define LEVEL spdlog::level::info
#endif

static void on_logger_error(const std::string &err_msg)
{
    std::printf("[Logger Error] Reason : %s", err_msg.c_str());
}

void Logger::init()
{
    if(alog != nullptr && clog != nullptr)
        return;

    alog = spdlog::android_logger_mt("app");
    alog->set_pattern(FORMAT);
    alog->set_level(LEVEL);
    alog->set_error_handler(on_logger_error);

    clog = spdlog::android_logger_mt("core");
    clog->set_pattern(FORMAT);
    clog->set_level(LEVEL);
    clog->set_error_handler(on_logger_error);
}

void Logger::set_format(spdlog::logger *logger, const char* format)
{
    logger->set_pattern(format);
    logger->flush();
}


}
