#pragma once

#include "globals.h"
#include <vector>
#include <jni.h>

namespace tools {

    JNIEXPORT void callContextCallBack(int code, std::initializer_list<std::string> args);
}
