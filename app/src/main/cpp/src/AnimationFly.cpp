#include <memory>

#include "api/AnimationFly.hpp"

#include "Sakura.hpp"

namespace anicore
{

    ref<AnimationFly> AnimationFly::CreateFly(int api)
    {
        ref<AnimationFly> result;
        switch (api)
        {
            case 0:
                result = std::make_shared<Sakura>();
                break;
            case 1:
                CWarn("API Can't Use At Now");
                break;
            default:
                CError("Api Not Support", "API Not Support");
                raise(1);
                break;
        }
        return result;
    }
}