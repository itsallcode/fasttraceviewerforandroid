package itsallcode.org.fasttraceviewerforandroid;

import android.app.Application;

import itsallcode.org.fasttraceviewerforandroid.dagger.components.AppComponent;
import itsallcode.org.fasttraceviewerforandroid.dagger.components.DaggerAppComponent;
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.FastTraceRepositoryModule;

/**
 * Created by thomasu on 2/2/18.
 */

public class FastTraceApp extends Application {
    private static FastTraceApp instance;

    private AppComponent applicationComponent;

    public static FastTraceApp getInstance() {
        return instance;
    }

    private static void setInstance(FastTraceApp instance) {
        FastTraceApp.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        applicationComponent = DaggerAppComponent
                .builder()
                .fastTraceRepositoryModule(new FastTraceRepositoryModule(this)).build();
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }

}
