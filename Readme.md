# NYTimesMovieReview
This application is my attempt at [Android Architecture Components][aacusage] usage.

![App Screenshot](screenshots/home_screen.png "NYTimes Movie Reviews")

I wanted to test out AAC and Kotlin Coroutines using NYTimes API. You will need to register [here][nytimesapi] and get your own api key for this app to work.

App uses a shared object library, to save the NY Times API key, to make it difficult (but not impossible) to be hacked. It also uses Room database for saving server response (not really needed but added for supporting future enhancements). 

Because I wanted this app to be as production ready as possible I integrated Crashlytics (will need google-services.json from your Firebase account), setup build variants for debug and release builds, and setup signing for the app with a local.properties file holding the passwords (obviously not checked in). The app also supports different icons and app identifiers, for debug and release builds. This allows us to install debug and release builds on same device, useful in testing.

App does dependency injection differently, using ActivityLifecycleCallbacks, similar to what is done in [AAC sample apps][aacsampleapp]. Lastly I used the navigation component library because I wanted the nav graph, and a drawer menu so that I can add new feature there. It also allows easy way to propagate fragment arguments.

Make sure to edit [native-lib.cpp][sofile] to add your NY Times API key.

### Architecture Components
This application implements the following concepts :
- ViewModel
- LiveData
- Room
- Navigation Drawer

### Libraries
* [Android Architecture Components][arch]
* [Kotlin Coroutines][coroutines]
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading

[arch]: https://developer.android.com/arch
[coroutines]: https://developer.android.com/kotlin/coroutines
[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
[aacsampleapp]: https://github.com/android/architecture-components-samples/tree/master/GithubBrowserSample
[sofile]: https://github.com/omermuhammed/NYTMovieReviews/blob/master/app/src/main/cpp/native-lib.cpp
[aacusage]: https://developer.android.com/topic/libraries/architecture/guide.html
[nytimesapi]: https://developer.nytimes.com/apis
