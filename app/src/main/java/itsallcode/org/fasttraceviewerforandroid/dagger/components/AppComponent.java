package itsallcode.org.fasttraceviewerforandroid.dagger.components;

import itsallcode.org.fasttraceviewerforandroid.StartActivity;
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.FastTraceRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by thomasu on 8/19/17.
 */
@Component(modules = {
        FastTraceRepositoryModule.class
})
@Singleton
public interface AppComponent {
    void inject(StartActivity activity);
}
