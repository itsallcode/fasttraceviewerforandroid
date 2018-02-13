package itsallcode.org.fasttraceviewerforandroid.dagger.components

import itsallcode.org.fasttraceviewerforandroid.dagger.modules.FastTraceRepositoryModule

import javax.inject.Singleton

import dagger.Component
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.AppModule
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.CacheModule
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.ContentModule
import itsallcode.org.fasttraceviewerforandroid.viewmodel.FastTraceViewModel
import itsallcode.org.fasttraceviewerforandroid.viewmodel.SpecListViewModel

/**
 * Created by thomasu on 8/19/17.
 */
@Component(modules = [AppModule::class, ContentModule::class, CacheModule::class, FastTraceRepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(viewModel: FastTraceViewModel)
    fun inject(viewModel: SpecListViewModel)
}
