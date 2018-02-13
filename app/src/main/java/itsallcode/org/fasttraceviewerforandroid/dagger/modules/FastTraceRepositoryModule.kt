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

@Module
class FastTraceRepositoryModule(application: Application) {

    private val mFastTraceDatabase: FastTraceDatabase

    init {
        mFastTraceDatabase = Room.databaseBuilder(
                application,
                FastTraceDatabase::class.java,
                FastTraceDatabase.DATABASE_NAME)
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

    @Singleton
    @Provides
    internal fun providesSpecificationItemDao(fastTraceDatabase: FastTraceDatabase): SpecificationItemDao {
        return fastTraceDatabase.specificationItemDao()
    }

    @Singleton
    @Provides
    internal fun providesSpecificationItemCoverDao(fastTraceDatabase: FastTraceDatabase): SpecificationItemCoverDao {
        return fastTraceDatabase.specificationItemCoverDao()
    }

    @Singleton
    @Provides
    internal fun providesFastTraceMappingDao(fastTraceDatabase: FastTraceDatabase): FastTraceSpecItemMappingDao {
        return fastTraceDatabase.fastTraceMappingDao()
    }

    @Singleton
    @Provides
    internal fun providesDependentSpecificationItemDao(fastTraceDatabase: FastTraceDatabase): DependentSpecificationItemDao {
        return fastTraceDatabase.dependentSpecificationItemDao()
    }

    @Provides
    @Singleton
    internal fun providesRepository(fastTraceDao: FastTraceDao,
                                    specificationItemDao: SpecificationItemDao,
                                    specificationItemCoverDao: SpecificationItemCoverDao,
                                    fastTraceMappingDao: FastTraceSpecItemMappingDao,
                                    dependentSpecificationItemDao: DependentSpecificationItemDao): FastTraceRepository {
        return FastTraceDataSource(fastTraceDao,
                specificationItemDao,
                specificationItemCoverDao,
                fastTraceMappingDao,
                dependentSpecificationItemDao)
    }
}
