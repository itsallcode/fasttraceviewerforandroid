package itsallcode.org.fasttraceviewerforandroid.dagger.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import itsallcode.org.fasttraceviewerforandroid.data.FastTraceDao;
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceDataSource;
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceDatabase;
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository;

@Module
public class FastTraceRepositoryModule {

    private FastTraceDatabase mFastTraceDatabase;

    public FastTraceRepositoryModule(final Application application) {
        mFastTraceDatabase = Room.databaseBuilder(
                application,
                FastTraceDatabase.class,
                FastTraceDatabase.DATABASE_NAME)
                .build();

    }

    @Singleton
    @Provides
    FastTraceDatabase providesFastTraceDatabase() {
        return mFastTraceDatabase;
    }


    @Singleton
    @Provides
    FastTraceDao providesFastTraceDao(final FastTraceDatabase fastTraceDatabase) {
        return fastTraceDatabase.fastTraceDao();
    }


    @Provides
    @Singleton
    FastTraceRepository providesRepository(final FastTraceDao fastTraceDao) {
        return new FastTraceDataSource(fastTraceDao);
    }
}
