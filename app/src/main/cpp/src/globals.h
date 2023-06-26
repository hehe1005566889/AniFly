#pragma once

#include <iostream>
#include <string>
#include <memory>
#include <vector>

#include "basic/Exception.hpp"
#include "basic/Logger.h"

namespace anicore
{
    template <typename T>
    using ref = std::shared_ptr<T>;
    template <typename T>
    using unique = std::unique_ptr<T>;

    template <typename T, typename... Args>
    constexpr ref<T> create_ref(Args &&...args)
    {
        return std::make_shared<T>(std::forward<Args>(args)...);
    }
    template <typename T, typename... Args>
    constexpr unique<T> create_unique(Args &&...args)
    {
        return std::make_unique<T>(std::forward<Args>(args)...);
    }

    using i8 = char;
    using u8 = unsigned char;
    using i16 = short;
    using u16 = unsigned short;
    using i32 = int;
    using u32 = unsigned int;
#ifdef cb_win
    using i64 = long long;
    using u64 = unsigned long long;
#else
    using i64 = long;
    using u64 = unsigned long;
#endif
    using uintptr = u64;

    typedef unsigned char cbyte;

#define DELETEOBJ(OBJ) memset(&obj, 0, sizeof(OBJ))
#define REMOVEPTR(PTR) memset(PTR, 0 sizeof(PTR))

}


void hello_from_lib();
