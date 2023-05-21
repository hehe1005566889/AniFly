#pragma ocne

#include <iostream>
#include <string>

namespace anicore
{
    struct CException : public std::exception
    {
        CException(const char *reason)
        : reason(reason)
        {}

        const char* reason;
    };

    struct NetWorkException : public CException
    {
        NetWorkException(int mode ,const char* url, const char* reason)
        : CException(reason),
          ActionUrl(url),
          ActionMode(mode)
        {}

        int ActionMode;
        const char* ActionUrl;
    };
}