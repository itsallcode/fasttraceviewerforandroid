package itsallcode.org.fasttraceviewerforandroid.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.MockModule;
import itsallcode.org.fasttraceviewerforandroid.viewmodel.FastTraceViewModel;

@Component(modules = {
        MockModule.class,
})
@Singleton
public interface AppTestComponent {
    void inject(FastTraceViewModel viewModel);
}
