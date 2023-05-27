#include <jni.h>

#include "globals.h"
#include "api/AnimationFly.hpp"

#define JS2CS(ENV, STRING) ENV->GetStringUTFChars(STRING, nullptr)
#define JI2CI(ORIGIN     ) static_cast<int>(ORIGIN)

#define CS2JS(ENV, STRING) ENV->NewStringUTF(STRING)

anicore::ref<anicore::AnimationFly> _api;
#define CHECK_AVALIABLE if(_api == nullptr) raise(3);

extern "C"
JNIEXPORT void JNICALL
Java_ink_flybird_anifly_network_AniCore_setLoggerLevel(JNIEnv *env, jobject thiz, jint level) {
    anicore::Logger::init();
    CInfo("AniCore Init -> :) Have Fun~");

    if(level >= 0 && level <= 6)
    SET_LEVEL(APP_LOGGER, static_cast<int>(level));
}

extern "C"
JNIEXPORT void JNICALL
Java_ink_flybird_anifly_network_AniCore_searchBangumi(JNIEnv *env, jobject thiz, jstring key_word,
                                                      jint page, jobject result) {

// 获取Kotlin类的引用
    jclass kotlinClass = env->GetObjectClass(result);

    // 获取Kotlin方法的ID
    jmethodID methodId = env->GetMethodID(kotlinClass, "Add",
                                          "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

    auto keyword = JS2CS(env,key_word);
    std::list<anicore::Bangumi> _result;
    _api->Search(keyword, JI2CI(page), _result);

    for(const auto& item : _result)
    {
        env->CallVoidMethod(result, methodId,
                            CS2JS(env, item.title.c_str()),
                            CS2JS(env, item.image.c_str()),
                            CS2JS(env, item.detil.c_str()),
                            CS2JS(env, item.url.c_str()),
                            CS2JS(env, item.time.c_str())
                            );
    }

    env->ReleaseStringUTFChars(key_word, nullptr);
    env->DeleteLocalRef(kotlinClass);
    _result.clear();
}

extern "C"
JNIEXPORT void JNICALL
Java_ink_flybird_anifly_network_AniCore_getBangumiByTheme(JNIEnv *env, jobject thiz, jstring theme,
                                                          jint page, jobject result) {

// 获取Kotlin类的引用
    jclass kotlinClass = env->GetObjectClass(result);

    // 获取Kotlin方法的ID
    jmethodID methodId = env->GetMethodID(kotlinClass, "Add",
                                          "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

    auto _theme = std::string(JS2CS(env, theme));
    std::list<anicore::Bangumi> _result;
    _api->GetTheme(_theme, JI2CI(page), _result);

    for(const auto& item : _result)
    {
        env->CallVoidMethod(result, methodId,
                            CS2JS(env, item.title.c_str()),
                            CS2JS(env, item.image.c_str()),
                            CS2JS(env, item.detil.c_str()),
                            CS2JS(env, item.url.c_str()),
                            CS2JS(env, item.time.c_str())
        );
    }

    env->DeleteLocalRef(kotlinClass);
    _result.clear();
}

extern "C"
JNIEXPORT void JNICALL
Java_ink_flybird_anifly_network_AniCore_getDailyBangumi(JNIEnv *env, jobject thiz, jint day, jobject result) {
    // 获取Kotlin类的引用
    jclass kotlinClass = env->GetObjectClass(result);

    // 获取Kotlin方法的ID
    jmethodID methodId = env->GetMethodID(kotlinClass, "Add",
                                          "(Ljava/lang/String;Ljava/lang/String;)V");

    std::list<anicore::BangumiPlayList> _list;
    _api->DailyBangumi(JI2CI(day), _list);

    for(const auto& item : _list)
    {
        env->CallVoidMethod(result, methodId,
                            CS2JS(env, item.title.c_str()),
                            CS2JS(env, item.url.c_str())
        );
    }

    env->DeleteLocalRef(kotlinClass);
    _list.clear();
}

extern "C"
JNIEXPORT void JNICALL
Java_ink_flybird_anifly_network_AniCore_getPlayPage(JNIEnv *env, jobject thiz, jstring url,
                                                    jobject page, jobject result, jobject rs) {
    CHECK_AVALIABLE

    anicore::BangumiPlayPage _page;
    _api->GetPlayPage(JS2CS(env, url), _page);

    jclass _page_class = env->GetObjectClass(page);
    jmethodID _page_class_set = env->GetMethodID(_page_class, "Set",
                                                 "(Ljava/lang/String;Ljava/lang/String;)V");
    env->CallVoidMethod(page, _page_class_set,
                        CS2JS(env, _page.title.c_str()),
                        CS2JS(env, _page.videoUrl.c_str()));
    env->DeleteLocalRef(_page_class);


    jclass _result_class = env->GetObjectClass(result);
    jmethodID _result_class_add = env->GetMethodID(_result_class, "Add",
                                                   "(Ljava/lang/String;Ljava/lang/String;)V");
    for(const auto& item : _page.result)
    {
        env->CallVoidMethod(result, _result_class_add,
                            CS2JS(env, item.title.c_str()),
                            CS2JS(env, item.url.c_str()));
    }
    env->DeleteLocalRef(_result_class);


    jclass _rs_class = env->GetObjectClass(rs);
    jmethodID _rs_class_Add = env->GetMethodID(_rs_class, "Add",
                                               "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    for(const auto& item : _page.recommand)
    {
        env->CallVoidMethod(rs, _rs_class_Add,
                            CS2JS(env, item.title.c_str()),
                            CS2JS(env, item.image.c_str()),
                            CS2JS(env, item.detil.c_str()),
                            CS2JS(env, item.url.c_str()),
                            CS2JS(env, item.time.c_str()));
    }
    env->DeleteLocalRef(_rs_class);

    _page.recommand.clear();
    _page.result.clear();
}

extern "C"
JNIEXPORT void JNICALL
Java_ink_flybird_anifly_network_AniCore_getPlayList(JNIEnv *env, jobject thiz, jstring url,
                                                    jobject page, jobject result, jobject rs) {
    CHECK_AVALIABLE

    anicore::BangumiDetilPage _page;
    _api->GetPlayList(JS2CS(env, url), _page);

    jclass _page_class = env->GetObjectClass(page);
    jmethodID _page_class_set = env->GetMethodID(_page_class, "Set",
                                                 "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    env->CallVoidMethod(page, _page_class_set,
                        CS2JS(env, _page.title.c_str()),
                        CS2JS(env, _page.location.c_str()),
                        CS2JS(env, _page.time.c_str()),
                        CS2JS(env, _page.type.c_str()),
                        CS2JS(env, _page.image.c_str()),
                        CS2JS(env, _page.update.c_str()),
                        CS2JS(env, _page.detail.c_str()));
    env->DeleteLocalRef(_page_class);


    jclass _result_class = env->GetObjectClass(result);
    jmethodID _result_class_add = env->GetMethodID(_result_class, "Add",
                                                   "(Ljava/lang/String;Ljava/lang/String;)V");
    for(const auto& item : _page.result)
    {
        env->CallVoidMethod(result, _result_class_add,
                            CS2JS(env, item.title.c_str()),
                            CS2JS(env, item.url.c_str()));
    }
    env->DeleteLocalRef(_result_class);


    jclass _rs_class = env->GetObjectClass(rs);
    jmethodID _rs_class_Add = env->GetMethodID(_rs_class, "Add",
                                               "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    for(const auto& item : _page.recommand)
    {
        env->CallVoidMethod(rs, _rs_class_Add,
                            CS2JS(env, item.title.c_str()),
                            CS2JS(env, item.image.c_str()),
                            CS2JS(env, item.detil.c_str()),
                            CS2JS(env, item.url.c_str()),
                            CS2JS(env, item.time.c_str()));
    }
    env->DeleteLocalRef(_rs_class);

    _page.recommand.clear();
    _page.result.clear();

}

extern "C"
JNIEXPORT void JNICALL
Java_ink_flybird_anifly_network_AniCore_initAPI(JNIEnv *env, jobject thiz, jint api) {
    _api = anicore::AnimationFly::CreateFly(static_cast<int>(api));
    CHECK_AVALIABLE
}