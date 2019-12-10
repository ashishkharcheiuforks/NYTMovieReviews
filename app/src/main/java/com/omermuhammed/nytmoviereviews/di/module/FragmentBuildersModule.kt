package com.omermuhammed.nytmoviereviews.di.module

import com.omermuhammed.nytmoviereviews.ui.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


// All fragments intended to use Dagger2 @Inject should be listed here
@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

//    @ContributesAndroidInjector
//    abstract fun contributeHomeFragment(): HomeFragment
}