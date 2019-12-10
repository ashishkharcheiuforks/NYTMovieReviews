package com.omermuhammed.nytmoviereviews.utils

// Retrieve the secret key from the .so file
// Note that this is not by any means 100% secure, just makes it harder
// to hack and also shows how JNI/Native C++ is more easier than feared.
object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}