/*-
 * #%L
 \* FastTraceViewerForAndroid
 * %%
 * Copyright (C) 2017 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package itsallcode.org.fasttraceviewerforandroid;

import android.app.Application;

import itsallcode.org.fasttraceviewerforandroid.dagger.components.AppComponent;
import itsallcode.org.fasttraceviewerforandroid.dagger.components.DaggerAppComponent;
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.AppModule;
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.CacheModule;
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.ContentModule;
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
                .appModule(new AppModule(this))
                .contentModule(new ContentModule(this))
                .cacheModule(new CacheModule(this))
                .fastTraceRepositoryModule(new FastTraceRepositoryModule(this)).build();
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }

}
