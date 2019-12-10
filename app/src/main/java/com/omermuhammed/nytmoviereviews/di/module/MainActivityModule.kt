package com.omermuhammed.nytmoviereviews.di.module

import com.omermuhammed.nytmoviereviews.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}