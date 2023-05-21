#pragma once

#include "globals.h"

#include <sstream>

namespace anicore
{
    
static std::string CreateUri(const char* base, std::initializer_list<const char*> replaces)
{
    std::stringstream ss;
    const char* ptr = base;
    for (auto str : replaces) {
        const char* pos = strstr(ptr, "{}");
        if (!pos) {
            break;
        }
        ss << std::string(ptr, pos);
        ss << str;
        ptr = pos + 2;
    }
    ss << ptr;
    return ss.str();
}

void EncodeUrl(std::string& str);
void DecodeUrl(std::string& str);

}