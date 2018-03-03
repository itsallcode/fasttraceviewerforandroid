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
