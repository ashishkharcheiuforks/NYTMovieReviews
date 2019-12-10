package com.omermuhammed.nytmoviereviews.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omermuhammed.nytmoviereviews.di.ViewModelFactory
import com.omermuhammed.nytmoviereviews.di.ViewModelKey
import com.omermuhammed.nytmoviereviews.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// All view models intended to use Dagger2 should be listed here
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(HomeViewModel::class)
//    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}