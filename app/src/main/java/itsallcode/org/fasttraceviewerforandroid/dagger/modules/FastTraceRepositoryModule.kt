package itsallcode.org.fasttraceviewerforandroid.dagger.modules

import android.app.Application
import android.arch.persistence.room.Room

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import itsallcode.org.fasttraceviewerforandroid.data.*
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceDataSource
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceDatabase
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository
import java.nio.file.Paths

@Module
class FastTraceRepositoryModule(application: Application) {

    private val mFastTraceDatabase: FastTraceDatabase

    init {
        mFastTraceDatabase = Room.databaseBuilder(
                application,
                FastTraceDatabase::class.java,
                Paths.get(application.cacheDir.absolutePath, FastTraceDatabase.DATABASE_NAME).toString())
                .build()
    }

    @Singleton
    @Provides
    internal fun providesFastTraceDatabase(): FastTraceDatabase {
        return mFastTraceDatabase
    }

    @Singleton
    @Provides
    internal fun providesFastTraceDao(fastTraceDatabase: FastTraceDatabase): FastTraceDao {
        return fastTraceDatabase.fastTraceDao()
    }

    @Provides
    @Singleton
    internal fun providesRepository(fastTraceDao: FastTraceDao): FastTraceRepository {
        return FastTraceDataSource(fastTraceDao)
    }
}
