// CHECKING IN THIS FILE AS THIS APP IS FOR DEMO ONLY,
// OTHERWISE ADD THIS FILENAME TO .gitignore

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_omermuhammed_nytmoviereviews_utils_Keys_apiKey(JNIEnv *env, jobject object) {
    std::string api_key = "wzbca5kyS6kYSzOYwDmSRwsGOZ03YDxe";
    return env->NewStringUTF(api_key.c_str());
}