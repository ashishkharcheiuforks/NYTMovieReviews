package com.omermuhammed.nytmoviereviews.di.component

import android.app.Application
import com.omermuhammed.nytmoviereviews.MainApp
import com.omermuhammed.nytmoviereviews.di.module.AppModule
import com.omermuhammed.nytmoviereviews.di.module.MainActivityModule
import com.omermuhammed.nytmoviereviews.di.module.MovieReviewsRepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    MovieReviewsRepositoryModule::class,
    MainActivityModule::class])

interface AppComponent {
    @Component.Builder
    interface Builder {

        // provide Application instance into DI
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    // this is needed because MainApp has @Inject
    fun inject(mainApp: MainApp)
}