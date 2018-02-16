package itsallcode.org.fasttraceviewerforandroid.dagger.modules;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import itsallcode.org.fasttraceviewerforandroid.platformaccess.AppExecutors;
import itsallcode.org.fasttraceviewerforandroid.platformaccess.CacheAccess;
import itsallcode.org.fasttraceviewerforandroid.platformaccess.ContentAccess;
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository;
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceTestRepository;
import itsallcode.org.fasttraceviewerforandroid.utils.SingleExecutors;

@Module
public class MockModule {
    private CacheAccess mCacheAccess = Mockito.mock(CacheAccess.class);
    private ContentAccess mContentAccess = Mockito.mock(ContentAccess.class);

    @Provides
    @Singleton
    FastTraceRepository providesRepository()
    {
        return new FastTraceTestRepository();
    }

    @Provides
    @Singleton
    AppExecutors providesAppExecutors() {
        return new SingleExecutors();
    }

    @Provides
    @Singleton
    CacheAccess providesCacheAccess() {
        return mCacheAccess;
    }

    @Provides
    @Singleton
    ContentAccess providesContentAccess() {
        return mContentAccess;
    }
}
